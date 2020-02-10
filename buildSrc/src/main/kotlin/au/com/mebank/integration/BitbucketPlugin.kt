package au.com.mebank.integration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.jackson.responseObject
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import javax.inject.Inject

data class TokenResponse(
    var accessToken: String = "",
    var scopes: String = "",
    var expiresIn: Int = 0,
    var refreshToken: String = "",
    var tokenType: String = ""
)

data class TeamResponse(
    var displayName: String = "",
    var uuid: String = ""
)

data class RepositoryResponse(
    var name: String = "",
    var uuid: String = ""
)

data class RepositoriesResponse(
    var size: Int = 0,
    var values: List<RepositoryResponse> = emptyList()
)

data class Link(
    var href : String = ""
)

data class BranchResponse(
    var name: String = "",
    var links: Map<String, Link> = mapOf()
)

data class BranchesResponse(
    var size: Int = 0,
    var values: List<BranchResponse> = emptyList()
)

/**
 * Not sure how to store/find the bitbucjket credentials
 */

data class BitbucketCredentials(
    var key: String = "",    //System.getProperty("BITBUCKET_CLIENT_KEY"),
    var secret: String = ""  //System.getProperty("BITBUCKET_CLIENT_SECRET")
)

open class BitbucketExtension
@Inject constructor(project: Project) {

  val repository = project.objects.property<String>().value(project.name)

  val credentials = project.objects.property<BitbucketCredentials>().value(
      BitbucketCredentials(
          if (project.hasProperty("BITBUCKET_CLIENT_KEY")) {
            project.property("BITBUCKET_CLIENT_KEY") as String
          } else {
            ""
          },
          if (project.hasProperty("BITBUCKET_CLIENT_SECRET")) {
            project.property("BITBUCKET_CLIENT_SECRET") as String
          } else {
            ""
          }
      )
  )
}

val mapper = ObjectMapper().registerKotlinModule().apply {
  configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
}

class BitbucketPlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "bitbucket"
  }

  open class ListBranches : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "List branches"
    }

    @Input
    val token = project.objects.property<String>()

    @Input
    val repository = project.objects.property<String>().value(project.name)

    @Internal
    val team = token.map { token ->
      //https://api.bitbucket.org/2.0/teams/mebank_digital?fields=display_name,uuid
      val (_, response, teamResponse) =
          Fuel.get("https://api.bitbucket.org/2.0/teams/mebank_digital", listOf("fields" to "display_name,uuid"))
              .header(Headers.CONTENT_TYPE, "application/json")
              .authentication()
              .bearer(token)
              .also {
                logger.debug("===============================================================")
                logger.debug("Teams Url: ${it.cUrlString()}")
                logger.debug("===============================================================")
              }
              .responseObject<TeamResponse>(mapper)
      logger.lifecycle("=============================")
      logger.lifecycle("${response.url}")
      logger.lifecycle("=============================")
      val uuid = teamResponse.fold(
          { success -> success.uuid },
          { failure -> failure.response.body().asString(null) }
      )
//      logger.info("================================")
//      logger.info("UUID : ${uuid}")
      uuid
    }

    @TaskAction
    fun action() {
      logger.info("===============================================================")
      logger.info("Token: ${token.get()}")
      logger.info("===============================================================")
      val message = team.map { uuid ->
        logger.debug("===============================================================")
        logger.debug("Team: ${uuid}")
        logger.debug("===============================================================")
        //https://api.bitbucket.org/2.0/repositories/{uuid}?pagelen=10&page=1&fields=size,values.uuid,values.links.clone&q=name+~+"integration-service-gradle-plugin"
        val (_, response, reposResponse) =
            Fuel.get("https://api.bitbucket.org/2.0/repositories/${uuid}",
                listOf(
                    "pagelen" to "10",
                    "page" to "1",
                    "fields" to "size,values.uuid,values.name,values.links.clone",
                    "q" to "name ~ \"${repository.get()}\""
                ))
                .header(Headers.CONTENT_TYPE, "application/json")
                .authentication()
                .bearer(token.get())
                .also {
                  logger.debug("===============================================================")
                  logger.debug("Repos Url: ${it.cUrlString()}")
                  logger.debug("===============================================================")
                }
                .responseObject<RepositoriesResponse>(mapper)
        logger.lifecycle("=============================")
        logger.lifecycle("${response.url}")
        logger.lifecycle("=============================")
        val repos = reposResponse.fold(
            { success -> success },
            { failure ->
              logger.error(failure.response.body().asString(null));
              RepositoriesResponse(0, emptyList())
            }
        )
        repos.values.map {
          //https://api.bitbucket.org/2.0/repositories/{team_uuid}/{repo_uuid}refs/branches?fields=size,values.name,links.self.href
          val (_, _, branchesResponse) =
              Fuel.get("https://api.bitbucket.org/2.0/repositories/${uuid}/${it.uuid}/refs/branches",
                  listOf(
                      "pagelen" to "10",
                      "page" to "1",
                      "fields" to "size,values.uuid,values.name"
                  ))
                  .header(Headers.CONTENT_TYPE, "application/json")
                  .authentication()
                  .bearer(token.get())
                  .also {
                    logger.debug("===============================================================")
                    logger.debug("Branches Url: ${it.cUrlString()}")
                    logger.debug("===============================================================")
                  }
                  .responseObject<BranchesResponse>(mapper)
          branchesResponse.fold(
              { success ->
                success.values.map { branch ->
                  "Branch ${branch.name} -> ${branch.links["self"]}"
                }.joinToString("\n")
              },
              { failure ->
                logger.error(failure.response.body().asString(null));
                ""
              }
          )
        }.joinToString("\n")
      }.get()
      logger.info("=====================================")
      logger.info("Branches:\n ${message}")
      logger.info("=====================================")
    }
  }

  open class ListPullRequests : DefaultTask() {

    init {
      group = "Other"
      description = "Generate the bindings template file"
    }

    @TaskAction
    fun action() {
//      project.run {
//        //https://api.bitbucket.org/2.0/teams/mebank_digital?fields=display_name,uuid
//        val (_, _, teamResponse) =
//            Fuel.get("https://api.bitbucket.org/2.0/teams/mebank_digital",
//                parameters = listOf("fields" to "display_name,uuid"))
//                .header(Headers.CONTENT_TYPE, "application/json")
//                .authentication()
//                .bearer(token)
//                .also {
//                  logger.info("===============================================================")
//                  logger.info("Teams: ${it}")
//                  logger.info("===============================================================")
//                }
//                .responseObject<TeamResponse>(mapper)
//        val uuid = teamResponse.fold(
//            { success -> success.uuid },
//            { failure -> failure.response.body().asString(null) }
//        )
//        logger.info("================================")
//        logger.info("UUID : ${uuid}")
//        logger.info("     : https://api.bitbucket.org/2.0/repositories/${uuid}")
//
////        https://api.bitbucket.org/2.0/repositories/{e4d2a1ac-df3b-4fe3-aa8d-095c0645652f}?pagelen=10&page=1&fields=size,values.uuid,values.links.clone&q=name+~+"integration-service-gradle-plugin"
//
////        logger.info(Fuel.get("https://api.bitbucket.org/2.0/repositories/${uuid}", listOf(
////                    "pagelen" to "10",
////                    "page" to "1"
//////                ,
//////                    "fields" to "size,values.uuid,values.display_name,values.links.clone",
//////                    "q" to "name ~ 'integration-service'"
////            ))
////            .header(Headers.CONTENT_TYPE, "application/json")
////            .authentication()
////            .bearer(token)
////            .cUrlString())
////        logger.info("================================")
//
//        val (_, re, repoResponse) =
//            Fuel.get("https://api.bitbucket.org/2.0/repositories/${uuid}",
//                listOf(
//                    "pagelen" to "10",
//                    "page" to "1",
//                    "fields" to "size,values.uuid,values.name,values.links.clone",
//                    "q" to "name ~ \"integration-service\""
//                ))
//                .header(Headers.CONTENT_TYPE, "application/json")
//                .authentication()
//                .bearer(token)
//                .also {
//                  logger.info("===============================================================")
//                  logger.info("Repos: ${it}")
//                  logger.info("===============================================================")
//                }
//                .responseObject<RepositoriesResponse>(mapper)
//        val repos = repoResponse.fold(
//            { success -> success },
//            { failure ->
//              logger.error(failure.response.body().asString(null));
//              RepositoriesResponse(0, emptyList())
//            }
//        )
//        logger.info("URL: ${re.url}")
//        logger.info("================================")
//        repos.values.forEach {
//          logger.info("Repository : ${it.name}")
//          logger.info("      Uuid : ${it.uuid}")
//        }
//        logger.info("================================")
//
////        https://api.bitbucket.org/2.0/repositories/{e4d2a1ac-df3b-4fe3-aa8d-095c0645652f}?pagelen=10&page=2&fields=*,size,values.links.self.href
//
//      }
    }
  }

  override fun apply(project: Project): Unit = project.run {

    /*
        buildscript {
          dependencies {
            classpath("")
          }
        }
    */

    val bitbucketExtension = extensions.create<BitbucketExtension>(EXTENSION_NAME, project)

//    bitbucketExtension.credentials.map {
//      logger.info("==============================")
//      logger.info("Key    : ${it.key}")
//      logger.info("Secret : ${it.secret}")
//      logger.info("==============================")
//    }

    val tokenProvider = bitbucketExtension.credentials.map { credentials ->
      logger.debug("==============================")
      logger.debug("Key    : ${credentials.key}")
      logger.debug("Secret : ${credentials.secret}")
      logger.debug("==============================")
      val (_, _, tokenResponse) = Fuel.post("https://bitbucket.org/site/oauth2/access_token",
          listOf("grant_type" to "client_credentials"))
          .authentication()
          .basic(credentials.key, credentials.secret)
          .responseObject<TokenResponse>(mapper)
      val token = tokenResponse.fold(
          { success -> success.accessToken },
          { failure -> "" }
      )
      logger.debug("================================")
      logger.debug("Token : ${token}")
      logger.debug("================================")
      token
    }

    val listBranches = tasks.register<ListBranches>("${EXTENSION_NAME}ListBranches") {
      token.set(tokenProvider)
      repository.set(bitbucketExtension.repository)
    }
  }
}