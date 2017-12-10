package com.tadaskay.gradle.autojooq.config

import org.gradle.api.DefaultTask

fun DefaultTask.pluginExt(): AutoJooqExtension {
    return project.extensions.getByType(AutoJooqExtension::class.java)
}
