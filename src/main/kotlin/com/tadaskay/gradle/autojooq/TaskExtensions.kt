package com.tadaskay.gradle.autojooq

import com.spotify.docker.client.DefaultDockerClient
import org.gradle.api.DefaultTask

fun DefaultTask.docker(): DefaultDockerClient {
    return DefaultDockerClient.fromEnv().build()
}

fun DefaultTask.pluginExt(): AutoJooqExtension {
    return project.extensions.getByType(AutoJooqExtension::class.java)
}
