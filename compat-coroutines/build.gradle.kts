plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
}

kotlin {
	jvm()
	js {
		browser()
		nodejs()
	}
	linuxX64()
	linuxArm64()
	macosArm64()
	iosArm64()
	iosSimulatorArm64()
	watchosArm32()
	watchosArm64()
	watchosSimulatorArm64()
	tvosArm64()
	tvosSimulatorArm64()
	mingwX64()
	wasmJs {
		browser()
		nodejs()
	}
	wasmWasi {
		nodejs()
	}

	val commonMain by sourceSets.getting {
		dependencies {
			api(projects.core)
			api(libs.kotlinx.coroutines)
		}
	}
}

library {
	name.set("Sentier for KotlinX.Coroutines")
	description.set("Kotlin Multiplatform observability API embedded in KotlinX.Coroutines")
	homeUrl.set("https://gitlab.com/opensavvy/sentier")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
	}
}
