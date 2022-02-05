package room

import com.example.countdown.HistoryDao
import java.lang.Appendable

class RepositoryImplementationLocal(
    private val dataSource:
    TranslatorApp.DataSourceLocal<List<DataModel>>
) :

    TranslatorApp.DataSourceLocal.RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    // Теперь наш локальный репозиторий работает. Передаём в конструктор
    // HistoryDao (вспоминаем в модуле Koin RoomDataBaseImplementation(get())).

    class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
        DataSourceLocal<List<DataModel>> {

        // Возвращаем список всех слов в виде понятного для Activity
        // List<SearchResult>
        override suspend fun getData(word: String): List<DataModel> {
            // Метод mapHistoryEntityToSearchResult описан во вспомогательном
            // классе SearchResultParser, в котором есть и другие методы для
            // трансформации данных
            return mapHistoryEntityToSearchResult(historyDao.all())

            // Метод сохранения слова в БД. Он будет использоваться в интеракторе

            override suspend fun saveToDB(appState: AppState) {
                convertDataModelSuccessToEntity(appState)?.let {
                    historyDao.insert(it)
                }
            }
        }

        fun mapHistoryEntityToSearchResult(list: List<Entity>): List<DataModel> {
            val dataModel = ArrayList<DataModel>()
            if (!list.isNullOrEmpty()) {
                for (entity in list) {
                    dataModel.add(DataModel(entity.word, null))

                }

            }
            return dataModel
        }

        fun convertDataModelSuccessToEntity(appState: AppState): Entity? {
            return when (appState){
                is AppState.Success -> {
                    val searchResult = appState.data
                    if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty){
                        null
                } else {
                    Entity(searchResult[0].text!!, null)
                    }

                }
                else -> null
            }
        }
    }
}




