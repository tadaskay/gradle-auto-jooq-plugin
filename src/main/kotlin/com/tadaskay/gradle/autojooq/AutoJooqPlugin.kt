package com.tadaskay.gradle.autojooq

import com.tadaskay.gradle.autojooq.config.AutoJooqExtension
import com.tadaskay.gradle.autojooq.migrations.Liquibase
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
        val migrateLiquibase = project.task("autoJooqMigrateLiquibase", Liquibase::class) {
            dependsOn(postgresUp)
        }
        project.task("autoJooqPostgresDown", PostgresDown::class) {
            mustRunAfter(migrateLiquibase)
        }
    }
}
