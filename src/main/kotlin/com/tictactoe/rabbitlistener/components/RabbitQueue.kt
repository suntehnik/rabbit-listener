package com.tictactoe.rabbitlistener.components

import com.tictactoe.rabbitlistener.components.RabbitQueue.Companion.QUEUE_NAME
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class RabbitQueue {

    @Bean
    fun queue() = Queue(QUEUE_NAME, true)

    @Bean
    fun receiver() = RabbitReceiver()

    @Bean
    fun sender(template: RabbitTemplate, queue: Queue) = Sender(template, queue)


    companion object {
        const val QUEUE_NAME = "spring-queue"
    }
}

class Sender(val template: RabbitTemplate, val queue: Queue) {
    init {
        System.out.println("Init sender")
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val message = "Hello World!"
        template.convertAndSend(queue.name, message)
        println("[x] Sent '$message'")
    }
}


@RabbitListener(queues = [QUEUE_NAME])
class RabbitReceiver {

    init {
        System.out.println("Init receiver")
    }

    @RabbitHandler
    fun receive(msgByte: Array<Byte>) {
        println("Byte received")
    }

    @RabbitHandler
    fun receive(messageIn: String) = Logger.getLogger("RabbitReceiver").log(Level.INFO, "[x] Received '$messageIn'")
}
