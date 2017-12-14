import org.gradle.kotlin.dsl.*

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.9.9"
}

group = "com.tadaskay.gradle"
version = "1.0.1"

gradlePlugin {
    (plugins) {
        "autoJooq" {
            id = "com.tadaskay.auto-jooq"
            implementationClass = "com.tadaskay.gradle.autojooq.AutoJooqPlugin"
        }
    }
}

pluginBundle {
    tags = listOf("jOOQ", "liquibase", "PostgreSQL", "docker")
    website = "https://github.com/tadaskay/gradle-auto-jooq-plugin"
    vcsUrl = "https://github.com/tadaskay/gradle-auto-jooq-plugin"
    (plugins) {
        "autoJooq" {
            id = "com.tadaskay.auto-jooq"
            displayName = "Gradle Automated jOOQ Plugin"
            description = "Gradle Automated jOOQ Plugin"
        }
    }
}

publishing {
    repositories {
        maven(url = "build/repository") {
            name = "test"
        }
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
