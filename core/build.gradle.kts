plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
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
