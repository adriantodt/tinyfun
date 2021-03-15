plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "net.adriantodt"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.jetbrains:annotations:20.1.0")
    implementation("com.grack:nanojson:1.7")
    implementation("net.adriantodt:tinypipe:1.0")
    implementation("org.yaml:snakeyaml:1.28")
}
