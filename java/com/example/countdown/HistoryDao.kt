package com.example.countdown

import androidx.room.*
import room.Entity


// Построение методов должно быть вам знакомо. Единственное, чему стоит
// уделить внимание - ключевое слово suspend, которое намекает, что все
// запросы в БД будут асинхронными (корутины поддерживаются в Room изначально)

@Dao
interface HistoryDao {
    // Получить весь список слов
    @Query("SELECT * FROM Entity")
    suspend fun all(): List<Entity>

    // Получить конкретное слово
    @Query("SELECT * FROM Entity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): Entity

    // Сохранить новое слово
    // onConflict = OnConflictStrategy.IGNORE означает, что дубликаты не будут
    // сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Entity)

    // Вставить список слов
    // onConflict = OnConflictStrategy.IGNORE означает, что дубликаты не будут
    // сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<Entity>)

    // Обновить слово
    @Update
    suspend fun update(entity: Entity)

    // Удалить слово
    @Delete
    suspend fun delete(entity: Entity)
}

