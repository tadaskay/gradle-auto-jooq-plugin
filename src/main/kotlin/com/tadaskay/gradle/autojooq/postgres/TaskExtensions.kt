package com.tadaskay.gradle.autojooq.postgres

import com.spotify.docker.client.DefaultDockerClient
import org.gradle.api.DefaultTask

fun DefaultTask.docker(): DefaultDockerClient {
    return DefaultDockerClient.fromEnv().build()
}

