package room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel(private val interactor: HistoryInteractor): BaseViewModel<AppState>{
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return loveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean){
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        ViewModelCoroutineScope.launch {startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean){
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word,isOnline)))
    }
    override fun handleError(error: Throwable){
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared(){
        _mutableLiveData.value = AppState.Success(null)

        super.onClearded
    }
}