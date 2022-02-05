package com.example.countdown

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

class NewsRemoteDataSource (
    private val newsApi: NewsApi,
    private val refreshIntervalMs: Long = 5000
    ){
    val latestNews = flow{
        while (true){
            val latestNews = newsApi.fetchLatestNews()
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }
}

interface NewsApi{
    suspend fun fetchLatestNews(): List<ArticleHeadline>
}