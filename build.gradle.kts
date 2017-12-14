import org.gradle.kotlin.dsl.*

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = "com.tadaskay.gradle"
version = "1.0.0"

gradlePlugin {
    (plugins) {
        "autoJooq" {
            id = "com.tadaskay.auto-jooq"
            implementationClass = "com.tadaskay.gradle.autojooq.AutoJooqPlugin"
        }
    }
}

publishing {
    repositories {
        maven(url = "build/repository")
    }
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib", "1.2.0"))
    compile("com.spotify:docker-client:8.10.0")
    compile("org.liquibase:liquibase-core:3.5.3")
    compile("nu.studer:gradle-jooq-plugin:2.0.9")
    runtime("org.postgresql:postgresql:42.1.4")
}
