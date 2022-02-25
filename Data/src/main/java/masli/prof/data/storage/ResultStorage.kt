package masli.prof.data.storage

import masli.prof.data.storage.models.ResultDataModel

interface ResultStorage {
    val allNotes: List<ResultDataModel>
    suspend fun insertResultData(resultData: ResultDataModel)
    suspend fun deleteResultData(resultData: ResultDataModel)
}