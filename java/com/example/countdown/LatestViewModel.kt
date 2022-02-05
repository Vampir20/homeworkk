package com.example.countdown


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LatestViewModel(
    private val newsRepository: NewsRepository
): ViewModel(){
    init {
        val viewModelScope =
        viewModelScope.launch{
            newsRepository.favoriteLatestNews.collect{favoriteNews->

            }
        }
    }
}

class NewsRepository {
    val favoriteLatestNews: Flow<String> = flow {
        while (true) {
            emit("News")
            delay(1_000)
        }
    }
}

