package com.bong.ch1

import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class Helper {
    companion object {
        private val log = LoggerFactory.getLogger(this.javaClass)

        fun finishedStage(): CompletionStage<Int> {
            val future = CompletableFuture.supplyAsync {
                log.info("return in future")
                1
            }

            Thread.sleep(100)
            return future
        }

        fun completionStage(): CompletionStage<Int> {
            return CompletableFuture.supplyAsync {
                Thread.sleep(1000)
                log.info("I'm Running")

                1
            }
        }

        fun completionStageAfter1S(): CompletionStage<Int> {
            return CompletableFuture.supplyAsync {
                log.info("getCompletionStage")
                Thread.sleep(1000)
                1
            }
        }

        fun addOne(value: Int): CompletionStage<Int> {
            return CompletableFuture.supplyAsync {
                Thread.sleep(100)
                value + 1
            }
        }

        fun addResultPrefix(value: Int): CompletionStage<String> {
            return CompletableFuture.supplyAsync {
                Thread.sleep(100)
                "result: $value"
            }
        }

        fun waitAndReturn(millis: Int, value: Int): CompletableFuture<Int> {
            return CompletableFuture.supplyAsync {
                log.info("waitAndReturn: {}ms", millis)
                Thread.sleep(millis.toLong())

                value
            }
        }
    }
}
