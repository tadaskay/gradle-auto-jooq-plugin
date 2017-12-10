package com.tadaskay.gradle.autojooq.postgres

import com.spotify.docker.client.DefaultDockerClient
import com.tadaskay.gradle.autojooq.AutoJooqExtension
import org.gradle.api.DefaultTask

fun DefaultTask.docker(): DefaultDockerClient {
    return DefaultDockerClient.fromEnv().build()
}

fun DefaultTask.pluginExt(): AutoJooqExtension {
    return project.extensions.getByType(AutoJooqExtension::class.java)
}
