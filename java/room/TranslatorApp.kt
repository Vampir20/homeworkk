package room

import androidx.room.Room
import coil.decode.DataSource
import org.koin.dsl.module
import retrofit2.Retrofit
import room.HistoryDataBase

class TranslatorApp{
val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class,
    "HistoryDB").build() }

    //получаем Dao
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>>{
        RepositoryImplementation(RetrofitImplementation())
    }

    single<RepositoryLocal<List<DataModel>>>{
        RepositoryImplementationLocal(RoomDatabaseImplementation(get()))
    }

}
    val mainScreen = module {
        factory { MainVieModel(get()) }
        factory { MainViewModel(get(), get()) }
    }

    val historyScreen = module {
        factory { HistoryVieModel(get()) }
        factory { HistoryViewModel(get(), get()) }
    }

    interface DataSourceLocal<T> : DataSource<T> {
        suspend fun saveToDB(appState: AppState)

    interface RepositoryLocal<T> : Repository<T>{
        suspend fun saveToDB(appState: AppState)
    }

    }
}