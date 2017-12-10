import org.gradle.kotlin.dsl.*

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = "com.tadaskay.gradle"
version = "1.0"

gradlePlugin {
    (plugins) {
        "autoJooq" {
            id = "auto-jooq"
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
}
