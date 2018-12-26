package com.tictactoe.rabbitlistener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RabbitListenerApplication {
}

fun main(args: Array<String>) {
    runApplication<RabbitListenerApplication>(*args)
}

