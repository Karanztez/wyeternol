plugins {
    alias(libs.plugins.shadow)
    application
}

dependencies {
    implementation(project(":wyeternol-core"))
    implementation(project(":wyeternol-world"))
    implementation(project(":wyeternol-game"))
    implementation("net.minestom:minestom:master-SNAPSHOT")
}

application {
    mainClass.set("dev.wyeternol.server.ServerRunnerKt")
}

tasks.jar {
    archiveClassifier.set("thin")
}

tasks.shadowJar {
    archiveBaseName.set("wyeternol-server")
    archiveClassifier.set("")
    archiveVersion.set("")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("build"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    exclude { it.name.endsWith(".kotlin_module") }
}
