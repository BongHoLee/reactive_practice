package com.bong.ch1.practice.completablefuture.blocking.repository

import com.bong.ch1.practice.completablefuture.common.repository.ImageEntity
import org.slf4j.LoggerFactory

class ImageRepository {
    private val log by lazy { LoggerFactory.getLogger(javaClass) }
    private val imageMap = mapOf(
        Pair("image#1000", ImageEntity("iamge#1000", "profileImage", "https://dailyone.com/images/1000"))
    )

    fun findById(id: String): ImageEntity? {
        log.info("ImageRepository.findById: {}", id)
        Thread.sleep(1000)
        return imageMap[id]
    }
}