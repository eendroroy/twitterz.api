package com.github.eendroroy.twitterz.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 *
 * @author indrajit
 */

@SpringBootApplication
class TZApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(TZApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<TZApplication>(*args)
}
