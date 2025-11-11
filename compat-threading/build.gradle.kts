plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
}

kotlin {
	jvm()

	sourceSets.commonMain {
		dependencies {
			api(projects.core)
		}
	}

	sourceSets.commonTest {
		dependencies {
			implementation(libs.prepared.kotest)
		}
	}

	sourceSets.all {
		languageSettings {
			enableLanguageFeature("ContextParameters")
		}
	}
}

library {
	name.set("Sentier for threads")
	description.set("Kotlin Multiplatform observability API embedded in thread locals")
	homeUrl.set("https://gitlab.com/opensavvy/sentier")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
	}
}
