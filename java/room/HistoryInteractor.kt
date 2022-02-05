package room

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
): Interactor<AppState>{
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState{
        return AppState.Success{
            if (fromRemoteSource){
                repositoryRemote
            } else{
                repositoryLocal
            }.getData(word)
        }
    }
}

