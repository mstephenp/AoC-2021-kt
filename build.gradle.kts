plugins {
    kotlin("jvm") version "1.6.0"
}

group = "advent.of.code"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
}