package com.tadaskay.gradle.autojooq.config

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setValue

open class AutoJooqExtension(project: Project) {

    private val dockerImageProperty = project.objects.property<String>()
    val dockerImageProvider: Provider<String> get() = dockerImageProperty
    var dockerImage by dockerImageProperty

    var containerId: String? = null
    var port: Int? = null

    var changeLogFile = "src/main/resources/db/changelog/db.changelog-master.xml"
    fun url(): String {
        return "jdbc:postgresql://localhost:$port/postgres"
    }

    lateinit var username: String
    lateinit var password: String

    init {
        dockerImageProperty.set("postgres:10.0-alpine")
    }

}
