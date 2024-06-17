import dev.kikugie.stonecutter.StonecutterSettings

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases")
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.4"
}

extensions.configure<StonecutterSettings> {
    kotlinController = true
    centralScript = "build.gradle.kts"
    shared { versions("1.21") }
    create(rootProject)
}
