package com.tadaskay.gradle.autojooq.jooq

import com.tadaskay.gradle.autojooq.config.pluginExt
import nu.studer.gradle.jooq.JooqExtension
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.withConvention
import org.jooq.util.jaxb.Database
import org.jooq.util.jaxb.Jdbc

open class ConfigureJooq : DefaultTask() {

    private val ext = pluginExt()

    @TaskAction
    fun configureJooqPlugin() {
        project.withConvention(JavaPluginConvention::class) {
            val jooqExt = project.extensions.getByType(JooqExtension::class.java)
            val config = jooqExt.configs["main"]!!.configuration
            config.withJdbc(Jdbc()
                .withUrl(ext.url())
                .withUser(ext.username)
                .withPassword(ext.password)
            )
            config.generator.withDatabase(Database()
                .withInputSchema("public")
            )
        }
    }
}
