package com.bong.ch1.practice.completablefuture.common.repository

data class ArticleEntity(
    val id: String,
    val title: String,
    val content: String,
    val userId: String
)