package com.tadaskay.gradle.autojooq.postgres

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class PostgresDown : DefaultTask() {

    private val docker = docker()
    private val ext = pluginExt()

    @TaskAction
    fun postgresDown() {
        if (ext.containerId != null) {
            docker.killContainer(ext.containerId)
        }
    }
}
