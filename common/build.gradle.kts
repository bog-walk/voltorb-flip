plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "dev.bogwalk"
version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop")

    sourceSets {
        all {
            jvmToolchain(17)
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.material3)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}