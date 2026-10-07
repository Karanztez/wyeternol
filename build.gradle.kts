plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.shadow) apply false
    `maven-publish`
}

allprojects {
    group = "dev.wyeternol"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            content {
                includeModule("net.minestom", "minestom")
                includeModule("net.minestom", "testing")
            }
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(25)
    }

    dependencies {
        add("implementation", "ch.qos.logback:logback-classic:1.5.17")
        add("implementation", "net.kyori:adventure-api:4.19.0")
        add("implementation", "net.kyori:adventure-text-minimessage:4.19.0")
    }

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/karanztez/wyeternol")
                credentials {
                    username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as? String ?: "karanztez"
                    password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key") as? String
                }
            }
        }
        publications {
            create<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}
