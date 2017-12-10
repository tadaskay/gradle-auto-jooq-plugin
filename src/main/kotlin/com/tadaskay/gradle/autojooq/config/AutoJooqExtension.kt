package com.tadaskay.gradle.autojooq.config

open class AutoJooqExtension {
    var containerId: String? = null
    var port: Int? = null

    var changeLogFile = "src/main/resources/db/changelog/db.changelog-master.xml"
    fun url() : String {
        return "jdbc:postgresql://localhost:$port/postgres"
    }
    lateinit var username: String
    lateinit var password: String
}
