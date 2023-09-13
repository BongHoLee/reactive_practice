package com.bong.ch1.practice.completablefuture.blocking

import com.bong.ch1.practice.completablefuture.blocking.repository.ArticleRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.FollowRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.ImageRepository
import com.bong.ch1.practice.completablefuture.blocking.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserBlockingServiceTest {

    private val userBlockingService by lazy {
        UserBlockingService(UserRepository(), ArticleRepository(), ImageRepository(), FollowRepository())
    }

    @Test
    fun `유효하지 않은 userId에 대해 null 반환`() {
        // given
        val userId = "NOT_VALID_USER_ID"

        // when
        val user = userBlockingService.getUserById(userId)

        // then
        assertThat(user).isNull()

    }
    @Test
    fun `블로킹 연산 수행`() {
        // given
        val userId = "1234"

        val start = System.currentTimeMillis()
        // when
        val user = userBlockingService.getUserById(userId)

        val elapsed = System.currentTimeMillis() - start

        // then
        assertThat(user).isNotNull
        assertThat(user!!.name).isEqualTo("beaoh")
        assertThat(elapsed).isGreaterThan(4000L)
    }

    @Test
    fun `non-blokcking 연산 수행`() {
        // given
        val userId = "1234"

        val start = System.currentTimeMillis()
        // when
        val user = userBlockingService.getUserByIdAsync(userId)

        val elapsed = System.currentTimeMillis() - start

        // then
        assertThat(user).isNotNull
        assertThat(user!!.name).isEqualTo("beaoh")
        assertThat(elapsed).isGreaterThan(2000L).isLessThan(3000L)
        println(elapsed)
    }
}