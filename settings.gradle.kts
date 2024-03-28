/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/8.1.1/userguide/multi_project_builds.html
 */

rootProject.name = "Sentier"

dependencyResolutionManagement {
	repositories {
		mavenCentral()
	}
}

pluginManagement {
	repositories {
		// region OpenSavvy Conventions

		maven {
			name = "opensavvy-gradle-conventions"
			url = uri("https://gitlab.com/api/v4/projects/51233470/packages/maven")

			metadataSources {
				gradleMetadata()
				mavenPom()
			}

			content {
				includeGroupAndSubgroups("dev.opensavvy")
			}
		}

		// endregion
		// region Standard repositories

		gradlePluginPortal()
		google()
		mavenCentral()

		// endregion
	}

	includeBuild("gradle/conventions")
}

plugins {
	id("dev.opensavvy.conventions.settings") version "1.2.0"
}

include(
	"gradle:templates:template-app",
	"gradle:templates:template-lib",

	"core",
	"compat-coroutines",
	"compat-threading",
)
