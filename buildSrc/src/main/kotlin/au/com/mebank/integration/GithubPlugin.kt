package au.com.mebank.integration

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.kohsuke.github.GitHubBuilder

/**
 * https://github-api.kohsuke.org
 */
class GithubPlugin : Plugin<Project> {
  override fun apply(project: Project): Unit = project.run {
/*
    val github = GitHubBuilder().withAppInstallationToken("my_installation_token").build()
    val github = GitHubBuilder.fromEnvironment().build();
*/

  }
}