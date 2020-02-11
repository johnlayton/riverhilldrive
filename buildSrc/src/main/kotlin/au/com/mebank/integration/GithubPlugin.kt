package au.com.mebank.integration

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.kohsuke.github.GitHubBuilder
import javax.inject.Inject

/**
 * https://github-api.kohsuke.org
 */
class GithubPlugin : Plugin<Project> {

  data class GithubCredentials(
      var key: String = "",    //System.getProperty("BITBUCKET_CLIENT_KEY"),
      var secret: String = ""  //System.getProperty("BITBUCKET_CLIENT_SECRET")
  )

  open class GithubExtension
  @Inject constructor(project: Project) {

//    val repository = project.objects.property<String>().value(project.name)
//
//    val credentials = project.objects.property<BitbucketCredentials>().value(
//        BitbucketCredentials(
//            if (project.hasProperty("BITBUCKET_CLIENT_KEY")) {
//              project.property("BITBUCKET_CLIENT_KEY") as String
//            } else {
//              ""
//            },
//            if (project.hasProperty("BITBUCKET_CLIENT_SECRET")) {
//              project.property("BITBUCKET_CLIENT_SECRET") as String
//            } else {
//              ""
//            }
//        )
//  }
  }

  companion object {
    const val EXTENSION_NAME = "github"
  }

  open class ListBranches : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "List branches"
    }

    @TaskAction
    fun action() {
      logger.lifecycle("===============================================================")
      logger.lifecycle("Repositories ... ")

//      val github = GitHubBuilder().withOAuthToken("...").build();
      val github = GitHubBuilder.fromEnvironment().build();

      github.getUser("johnlayton").listRepositories().asList().forEach {
        logger.lifecycle("\t${it.fullName}")

      }
      logger.lifecycle("===============================================================")

    }
  }

  override fun apply(project: Project): Unit = project.run {

    val githubExtension = extensions.create<GithubExtension>(EXTENSION_NAME, project)

/*
    val github = GitHubBuilder().withAppInstallationToken("my_installation_token").build()
    val github = GitHubBuilder.fromEnvironment().build();
*/
    val listBranches = tasks.register<ListBranches>("${EXTENSION_NAME}ListBranches")
//    {
//      token.set(tokenProvider)
//      repository.set(bitbucketExtension.repository)
//    }
  }
}