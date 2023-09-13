package com.bong.ch1.practice.completablefuture.common

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val profileImage: Image,
    val articleList: List<Article>,
    val followCount: Long
)