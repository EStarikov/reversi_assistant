//import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.0.21"
    //kotlin("plugin.compose") version "2.0.21"
    //id("org.jetbrains.compose") version "1.7.3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    //google()
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.h2database:h2:2.2.224")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")
    testImplementation("org.xerial:sqlite-jdbc:3.44.1.0")

    implementation("org.jetbrains.exposed:exposed-core:0.51.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.51.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.51.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.51.1")

    implementation("org.xerial:sqlite-jdbc:3.47.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    //implementation(compose.desktop.currentOs)
    //implementation(compose.material3)
    //implementation(compose.materialIconsExtended)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
/***
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ReversiGame"
            packageVersion = "1.0.0"
        }
    }
}
 ***/