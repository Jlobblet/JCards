import java.nio.file.Path as FilePath

val modName = "JCards"
val rootDir: FilePath = FilePath.of("C:", "Program Files (x86)", "Steam", "steamapps")
val stsDir: FilePath = rootDir.resolve(FilePath.of("common", "SlayTheSpire"))
val stsJar: FilePath = stsDir.resolve(FilePath.of("desktop-1.0.jar"))
val workshopDir: FilePath = rootDir.resolve(FilePath.of("workshop", "Content", "646570"))
val modTheSpireJar: FilePath = workshopDir.resolve(FilePath.of("1605060445", "ModTheSpire.jar"))
val baseModJar: FilePath = workshopDir.resolve(FilePath.of("1605833019", "BaseMod.jar"))
val stsLibJar: FilePath = workshopDir.resolve(FilePath.of("1609158507", "StSLib.jar"))

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.john"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    compileOnly(files(stsJar.toString()))
    compileOnly(files(modTheSpireJar.toString()))
    compileOnly(files(baseModJar.toString()))
    compileOnly(files(stsLibJar.toString()))
}

kotlin {
    sourceSets {
        val main by getting
        val test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.register<Jar>("makeJar") {
    from(sourceSets.main.get().output) { }
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Copy>("copyJarToStsMods") {
    dependsOn("clean", "makeJar")
    from("build/libs/$modName-$version.jar")
    into(stsDir.resolve(FilePath.of("mods")))
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register<Copy>("copyJarToWorkshop") {
    dependsOn("clean", "makeJar")
    from("build/libs/$modName-$version.jar")
    into(stsDir.resolve(FilePath.of(modName, "content")))
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
