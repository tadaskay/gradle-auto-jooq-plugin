package com.tadaskay.gradle.autojooq.postgres

import com.spotify.docker.client.DockerClient
import com.spotify.docker.client.messages.ContainerConfig
import com.spotify.docker.client.messages.HostConfig
import com.spotify.docker.client.messages.PortBinding
import com.tadaskay.gradle.autojooq.config.pluginExt
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class PostgresUp : DefaultTask() {

    private val docker = docker()
    private val ext = pluginExt()

    @TaskAction
    fun postgresUp() {
        val containerId = postgresContainer()
        docker.startContainer(containerId)
        ext.containerId = containerId
        val port = docker.inspectContainer(containerId).networkSettings().ports()
            ?.values?.flatten()?.first()?.hostPort()?.toInt()
        ext.port = port

        ext.username = "postgres"
        ext.password = ""

        waitForHealth()
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
            .build()
        )

        return postgresContainer.id()!!
    }

    private fun waitForHealth() {
        logger.info("Waiting for postgres healthcheck...")

        val createHealthCheckCommand = fun(): String {
            return docker.execCreate(
                ext.containerId,
                arrayOf("pg_isready", "-U", "postgres"),
                DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr()
            ).id()
        }

        var ready: Boolean
        var retries = 50
        do {
            val output = docker.execStart(createHealthCheckCommand())
            val execOut = output.readFully()
            logger.info(execOut)

            ready = execOut.contains("accepting")
            if (!ready) {
                Thread.sleep(100)
            }
        } while (!ready && retries-- > 0)

        if (!ready) {
            throw GradleException("Failed to start postgres container")
        }
    }
}

