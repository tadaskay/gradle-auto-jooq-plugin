package com.tadaskay.gradle.autojooq

import com.tadaskay.gradle.autojooq.postgres.PostgresDown
import com.tadaskay.gradle.autojooq.postgres.PostgresUp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.task

open class AutoJooqPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val autoJooq = project.extensions.create(
            "autoJooq",
            AutoJooqExtension::class.java
        )

        val postgresUp = project.task("autoJooqPostgresUp", PostgresUp::class) {

        }
        project.task("autoJooqPostgresDown", PostgresDown::class) {
            dependsOn(postgresUp)
        }
    }
}
