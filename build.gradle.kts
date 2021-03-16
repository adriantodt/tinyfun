plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "net.adriantodt"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

application.mainClass.set("net.adriantodt.tinyfun.server.TinyFunServer")

dependencies {
    val jettyVersion = "11.0.1"
    implementation("org.eclipse.jetty:jetty-server:${jettyVersion}")
    implementation("org.eclipse.jetty:jetty-servlet:${jettyVersion}")
    // implementation("org.eclipse.jetty:jetty-webapp:${jettyVersion}")
    // implementation("com.sparkjava:spark-core:2.9.3")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha5") {
        exclude(group = "com.sun.mail", module = "javax.mail")
        exclude(group = "edu.washington.cs.types.checker", module = "checker-framework")
    }
    implementation("org.jetbrains:annotations:20.1.0")
    implementation("com.grack:nanojson:1.7")
    // implementation("net.adriantodt:tinypipe:1.0")
    implementation("org.yaml:snakeyaml:1.28")
}
