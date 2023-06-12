package com.bong.ch1

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class CompletionStageTest {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `thenAccept 테스트`() {
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
}
