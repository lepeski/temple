plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "com.templebuilder"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
}

tasks.register<JavaExec>("geometryTest") {
    group = "verification"
    description = "Runs the deterministic geometry smoke tests."
    classpath = sourceSets.test.get().runtimeClasspath
    mainClass.set("com.templebuilder.geometry.TempleGeometryTestRunner")
}

tasks.test {
    dependsOn("geometryTest")
    enabled = false
}

