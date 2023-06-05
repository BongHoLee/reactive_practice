package com.bong

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BongApplication

fun main(args: Array<String>) {
    runApplication<BongApplication>(*args)
}
