package room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import coil.decode.DataSource
import com.example.countdown.HistoryDao
import org.koin.dsl.module

@Database(entities = arrayOf(Entity::class), version = 1, exportSchema = false)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}