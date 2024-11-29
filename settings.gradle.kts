pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("multiplatform").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("org.jetbrains.kotlin.plugin.compose").version(kotlinVersion)
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "voltorb-flip"

include(":common")
include(":desktop")
