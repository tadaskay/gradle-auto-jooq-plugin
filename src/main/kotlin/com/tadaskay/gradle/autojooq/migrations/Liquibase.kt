package com.tadaskay.gradle.autojooq.migrations

import com.tadaskay.gradle.autojooq.config.pluginExt
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class Liquibase : DefaultTask() {

    private val ext = pluginExt()

    @TaskAction
    fun liquibase() {
        val args = arrayListOf(
            "--changeLogFile=${ext.changeLogFile}",
            "--url=${ext.url()}",
            "--username=${ext.username}",
            "--password=${ext.password}",
            "update"
        )
        liquibase.integration.commandline.Main.run(args.toTypedArray())
    }
}
