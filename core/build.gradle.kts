plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
	alias(libsCommon.plugins.testBalloon)
}

kotlin {
	jvm()
	js(IR) {
		browser()
		nodejs()
	}
	linuxX64()
	iosArm64()
	iosSimulatorArm64()
	iosX64()

	sourceSets.commonTest.dependencies {
		implementation(libsCommon.opensavvy.prepared.testBalloon)
		implementation(libsCommon.kotlin.test)
	}

	sourceSets.all {
		languageSettings {
			enableLanguageFeature("ContextParameters")
		}
	}
}

library {
	name.set("Sentier")
	description.set("Kotlin Multiplatform observability API")
	homeUrl.set("https://gitlab.com/opensavvy/sentier")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
	}
}
