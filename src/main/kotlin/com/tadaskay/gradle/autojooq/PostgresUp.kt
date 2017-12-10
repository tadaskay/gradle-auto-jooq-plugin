package com.tadaskay.gradle.autojooq

import com.spotify.docker.client.messages.ContainerConfig
import com.spotify.docker.client.messages.HostConfig
import com.spotify.docker.client.messages.PortBinding
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class PostgresUp : DefaultTask() {

    private val docker = docker()
    private val ext = pluginExt()

    @TaskAction
    fun postgresUp() {
        val containerId = postgresContainer()
        docker.startContainer(containerId)
        ext.containerId = containerId
    }

    private fun postgresContainer(): String {
        val portBindings = mapOf<String, List<PortBinding>>(
            "5432" to listOf(PortBinding.randomPort("0.0.0.0"))
        )
        val hostConfig = HostConfig.builder()
            .portBindings(portBindings)
            .build()

        val postgresContainer = docker.createContainer(ContainerConfig.builder()
            .image("postgres:10.0-alpine")
            .exposedPorts("5432")
            .hostConfig(hostConfig)
            .build())

        return postgresContainer.id()!!
    }
}

