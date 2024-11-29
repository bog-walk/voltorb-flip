plugins {
    kotlin("multiplatform") apply false
    id("org.jetbrains.compose") apply false
    id("org.jetbrains.kotlin.plugin.compose") apply false
    kotlin("jvm") version "2.0.20"
}

group = "dev.bogwalk"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
    }
}