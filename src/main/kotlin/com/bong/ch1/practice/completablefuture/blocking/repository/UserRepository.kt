package com.bong.ch1.practice.completablefuture.blocking.repository

import com.bong.ch1.practice.completablefuture.common.repository.UserEntity
import org.slf4j.LoggerFactory

class UserRepository {
    private val log by lazy { LoggerFactory.getLogger(javaClass) }
    private val userMap = mapOf(
        Pair("1234", UserEntity("1234", "beaoh", 32, "image#1000"))
    )

    fun findById(userId: String): UserEntity? {
        log.info("UserRepository.findById: {}", userId)
        Thread.sleep(1000)
        return userMap[userId]
    }
}