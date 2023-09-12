package com.bong.ch1

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class CompletionStageTest {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `이전 stage가 done 상태에서 thenAccept 실행 시 caller 스레드 그대로 사용 테스트`() {
        log.info("start main")
        val completionStage = Helper.finishedStage()

        completionStage
            .thenAccept {
                log.info("{} in thenAccept", it)
            }
            .thenAccept {
                log.info("{} in thenAccept2", it)
            }

        log.info("after thenAccept")
    }

    @Test
    fun `이전 stage가 done 상태에서 thenAcceptAsync 실행 시 스레드 풀 사용 테스트`() {
        log.info("start main")
        val completionStage = Helper.finishedStage()
        val executor = Executors.newSingleThreadExecutor()

        completionStage
            .thenAcceptAsync({ log.info("{} in thenAccept", it) }, executor)
            .thenAccept {
                log.info("{} in thenAccept2", it)
            }

        log.info("after thenAccept")
        Thread.sleep(1000)
    }

    @Test
    fun `done 상태가 아닌 thenAccept 실행 시 이전 stage 실행 스레드와 다른 스레드 사용 테스트`() {
        log.info("start main")
        val completionStage = Helper.runningStage()

        completionStage
            .thenAccept {
                log.info("{} in thenAccept", it)
            }
            .thenAccept {
                log.info("{} in thenAccept2", it)
            }

        log.info("after thenAccept")
        Thread.sleep(2000)
    }

    @Test
    fun `done 상태가 아닌 thenAcceptAsync 실행 시 이전 stage 실행 스레드와 다른 스레드 사용 테스트`() {
        log.info("start main")
        val completionStage = Helper.runningStage()

        completionStage
            .thenAcceptAsync {
                log.info("{} in thenAccept", it)
            }
            .thenAccept {
                log.info("{} in thenAccept2", it)
            }

        log.info("after thenAccept")
        Thread.sleep(2000)
    }

    @Test
    fun `thenCompose{Async}의 경우 중첩된 CompletionStage 연산임 - flatMap의 평탄화와 유사하다`() {
        val stage = Helper.completionStage()
        stage.thenComposeAsync {
            val next = Helper.addOne(it)
            log.info("in thenComposeAsync : {}", next)
            next
        }.thenComposeAsync {
            val next = Helper.addResultPrefix(it)
            log.info("in thenComposeAsync2 : {}", next)
            next
        }.thenAcceptAsync {
            log.info("{} in thenAcceptAsync", it)
        }

        Thread.sleep(2000)
    }
}
