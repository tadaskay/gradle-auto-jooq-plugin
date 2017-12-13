package com.tadaskay.gradle.autojooq

import com.tadaskay.gradle.autojooq.config.AutoJooqExtension
import com.tadaskay.gradle.autojooq.jooq.ConfigureJooq
import com.tadaskay.gradle.autojooq.migrations.Liquibase
import com.tadaskay.gradle.autojooq.postgres.PostgresDown
import com.tadaskay.gradle.autojooq.postgres.PostgresUp
import nu.studer.gradle.jooq.JooqPlugin
import nu.studer.gradle.jooq.JooqTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.task

open class AutoJooqPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val ext = project.extensions.create(
            "autoJooq",
            AutoJooqExtension::class.java,
            project
        )

        val autoJooq = project.task("autoJooq", DefaultTask::class) {
        }

        val postgresUp = project.task("autoJooqPostgresUp", PostgresUp::class) {
            provideDockerImage(ext.dockerImageProvider)
        }
        val postgresDown = project.task("autoJooqPostgresDown", PostgresDown::class) {
        }

        val migrateLiquibase = project.task("autoJooqMigrateLiquibase", Liquibase::class) {
            dependsOn(postgresUp)
            mustRunAfter(postgresUp)
        }

        val configureJooq = project.task("autoJooqConfigureJooq", ConfigureJooq::class) {
            dependsOn(postgresUp)
            mustRunAfter(postgresUp)
        }

        project.plugins.apply(JooqPlugin::class.java)
        project.tasks.whenTaskAdded(closureOf<Task> {
            if (this is JooqTask) {
                this.dependsOn(postgresUp, migrateLiquibase, configureJooq)
                autoJooq.dependsOn(this)
                this.finalizedBy(postgresDown)
            }
        })
    }
}
