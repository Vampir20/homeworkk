package room


fun parseOnlineSearchResults(appState: AppState, dataModels: List<Any>): AppState {
    return AppState.Success(mapResult(appState, true))
}

fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

private fun mapResult(
    appState: AppState,
    isOnline: Boolean,
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
    }
    return newSearchResults

}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>

) {
    val dataModels: List<DataModel> = appState.data as List<DataModel>
    if (isOnline) {
        for (searchResult in dataModels) {
            parseOnlineSearchResult(searchResult, newDataModels)
        }
    } else {
        for (searchReult in dataModels) {
            newDataModels.add(DataModel(searchReult.text, arrayListOf()))
        }
    }
}

private fun parseOnlineSearchResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun mapEntityToSearchResult(list: List<Entity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): Entity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                Entity(searchResult[0].text, null)
            }
        }
        else -> null
    }

}

fun convertMeaningsToString(meanings: List<Meanings>): String{
    var meaningsSeparetedByComma = String()
    for ((index, meaning) in meanings.withIndex()){
        meaningsSeparetedByComma += if (index + 1 != meanings.size){
            String.format("%s%s", meaning.translation?.translationm ", ")
        }else{
            meaning.translation?.translation
        }
    }
    return meaningsSeparetedByComma
}