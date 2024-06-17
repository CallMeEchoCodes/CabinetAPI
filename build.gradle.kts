import java.net.URI

plugins {
    `java-library`
    id("fabric-loom") version "1.6.+"

    `maven-publish`
}

class ModInfo {
    val id = property("mod.id").toString()
    val group = property("mod.group").toString()
    val version = property("mod.version").toString()
    val minecraft_target = property("mod.minecraft_target").toString()
}

val mod = ModInfo()
val loaderVersion = property("deps.fabric_loader").toString()

sourceSets {
    create("testmod") {
        runtimeClasspath += main.get().runtimeClasspath
        compileClasspath += main.get().compileClasspath
    }
}

loom {
    runConfigs.all {
        runDir = "../../run"
    }

    runConfigs.create("testmodClient") {
        client()
        name = "Testmod Client"
        source(sourceSets["testmod"])

        ideConfigGenerated(true)
    }

    runConfigs.create("testmodServer") {
        server()
        name = "Testmod Server"
        source(sourceSets["testmod"])
        runDir = "../../run/server"

        ideConfigGenerated(true)
    }

    createRemapConfigurations(sourceSets["testmod"])
    accessWidenerPath = file("../../src/main/resources/cabinetapi.accesswidener")
}

repositories {
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:${stonecutter.current.version}")
    mappings("net.fabricmc:yarn:${property("deps.yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    modImplementation("com.terraformersmc:modmenu:${property("deps.modmenu")}") {
        isTransitive = false
    }

    "testmodImplementation"(sourceSets["main"].output)
}


tasks.processResources {
    inputs.property("id", mod.id)
    inputs.property("version", mod.version)
    inputs.property("minecraft_target", mod.minecraft_target)

    val map = mapOf(
        "id" to mod.id,
        "version" to mod.version,
        "minecraft_target" to mod.minecraft_target
    )

    filesMatching("fabric.mod.json") { expand(map) }
}

java {
    withSourcesJar()
    withJavadocJar()
}

extensions.configure(PublishingExtension::class.java) {
    publications {
        create<MavenPublication>("maven") {
            groupId = mod.group
            artifactId = mod.id
            version = "${mod.version}+${stonecutter.current.version}"
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "EchosMavenReleases"
            url = URI("https://maven.callmeecho.dev/releases")
            credentials(PasswordCredentials::class)
        }
    }
}


tasks.javadoc {
    with(options as StandardJavadocDocletOptions) {
        source = "21"
        encoding = "UTF-8"
        charSet = "UTF-8"
        memberLevel = JavadocMemberLevel.PACKAGE
        (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
    }
    classpath = sourceSets.main.get().compileClasspath
}
