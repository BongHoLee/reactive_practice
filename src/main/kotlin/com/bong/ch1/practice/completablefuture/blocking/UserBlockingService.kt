package com.bong.ch1.practice.completablefuture.blocking

import com.bong.ch1.practice.completablefuture.blocking.repository.ArticleRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.FollowRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.ImageRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.UserRepository
import com.bong.ch1.practice.completablefuture.common.Article
import com.bong.ch1.practice.completablefuture.common.Image
import com.bong.ch1.practice.completablefuture.common.User
import com.bong.ch1.practice.completablefuture.common.repository.UserEntity
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class UserBlockingService(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val imageRepository: ImageRepository,
    private val followRepository: FollowRepository,
) {
    private val log by lazy { LoggerFactory.getLogger(javaClass) }

    fun getUserById(id: String): User? =
        userRepository.findById(id)?.let { userEntity ->
            User(
                userEntity.id,
                userEntity.name,
                userEntity.age,
                image(userEntity),
                articles(userEntity),
                followCount(userEntity)
            )
        }

    fun getUserByIdAsync(id: String): User? =
        userRepository.findById(id)?.let {userEntity ->
            val image = async { image(userEntity) }
            val articles = async { articles(userEntity) }
            val followCount = async { followCount(userEntity) }

            CompletableFuture.allOf(image, articles, followCount)
                .thenAcceptAsync { log.info("Three futures are completed") }
                .thenRunAsync { log.info("Three futures are also completed") }
                .thenApplyAsync {
                    User(
                        userEntity.id,
                        userEntity.name,
                        userEntity.age,
                        image.join(),
                        articles.join(),
                        followCount.join()
                    )
                }.join()
        }
    private fun followCount(userEntity: UserEntity): Long =
        followRepository.countByUserId(userEntity.id)

    private fun articles(userEntity: UserEntity): List<Article> = articleRepository.findAllByUserId(userEntity.id)
        .map { articleEntity -> Article(articleEntity.id, articleEntity.title, articleEntity.content) }

    private fun image(userEntity: UserEntity): Image =
        imageRepository.findById(userEntity.profileImageId)
            ?.let { imageEntity -> Image(imageEntity.id, imageEntity.name, imageEntity.url) } ?: Image("Nothing", "Nothing", "Nothing")

}

fun <T> async(supplier: Supplier<T>): CompletableFuture<T> =
    CompletableFuture.supplyAsync { supplier.get() }
