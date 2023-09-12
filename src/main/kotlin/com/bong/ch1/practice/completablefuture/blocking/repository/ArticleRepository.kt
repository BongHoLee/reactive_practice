package com.bong.ch1.practice.completablefuture.blocking.repository

import com.bong.ch1.practice.completablefuture.common.repository.ArticleEntity
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

@Slf4j
class ArticleRepository {
    private val log by lazy { LoggerFactory.getLogger(javaClass) }
    companion object {
        private val articleEntities = listOf(
            ArticleEntity("1", "소식1", "내용1", "1234"),
            ArticleEntity("2", "소식2", "내용2", "1234"),
            ArticleEntity("3", "소식3", "내용3", "10000")
        )
    }

    fun findAllByUserId(userId: String): List<ArticleEntity> {
        log.info("ArticleRepository.findAllByUserId: {}", userId)
        Thread.sleep(1000)

        return articleEntities.filter { it.userId == userId }
    }
}