package com.bong.ch1.practice.completablefuture.blocking

import com.bong.ch1.practice.completablefuture.blocking.repository.ArticleRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.FollowRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.ImageRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.UserRepository
import com.bong.ch1.practice.completablefuture.common.Article
import com.bong.ch1.practice.completablefuture.common.Image
import com.bong.ch1.practice.completablefuture.common.User

class UserBlockingService(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val imageRepository: ImageRepository,
    private val followRepository: FollowRepository,
) {

    fun getUserById(id: String): User? =
        userRepository.findById(id)?.let { userEntity ->
            val image = imageRepository.findById(userEntity.profileImageId)
                ?.let { imageEntity -> Image(imageEntity.id, imageEntity.name, imageEntity.url) }

            val articles = articleRepository.findAllByUserId(userEntity.id)
                .map { articleEntity -> Article(articleEntity.id, articleEntity.title, articleEntity.content) }

            val followCount = followRepository.countByUserId(userEntity.id)


            User(
                userEntity.id,
                userEntity.name,
                userEntity.age,
                image,
                articles,
                followCount
            )
        }
}