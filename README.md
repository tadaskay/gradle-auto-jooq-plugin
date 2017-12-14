# gradle-auto-jooq-plugin

[jOOQ](https://www.jooq.org/) generates Java code from your database and lets you build type safe SQL queries through its fluent API.

This plugin seamlessly integrates jOOQ into your [Gradle](https://gradle.org/)-based project.

# How it works

1. Starts a DB container using docker
1. Runs your DB migrations
1. Runs jOOQ to generate Java classes from your effective DB schema
1. Tears down DB container

# Supported tools

* Databases:
    * Postgresql
* DB migrations:
    * Liquibase

# Minimal configuration

```groovy
plugins {
    id 'java' // or id 'groovy'
    id 'com.tadaskay.auto-jooq' version '1.0'
}

jooq {
    main(sourceSets.main) {
        generator {
            target {
                packageName = 'com.my.example.schema'
            }
        }
    }
}

dependencies {
    jooqRuntime 'org.postgresql:postgresql:42.1.4'
}
```

For more jOOQ configuration options, refer to [gradle-jooq-plugin](https://github.com/etiennestuder/gradle-jooq-plugin), to which 
jOOQ generation task is delegated.
