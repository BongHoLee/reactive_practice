package com.bong.ch1.practice.completablefuture.blocking.repository

import org.slf4j.LoggerFactory

class FollowRepository {
    private val log by lazy { LoggerFactory.getLogger(javaClass) }
    private val userFollowCountMap = mapOf(Pair("1234", 1000L))

    fun countByUserId(userId: String): Long {
        log.info("FollowRepository.countByUserId: {}", userId)
        Thread.sleep(1000)
        return userFollowCountMap.getOrDefault(userId, 0L)
    }
}