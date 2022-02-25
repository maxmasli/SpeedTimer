package masli.prof.domain.repositories

import masli.prof.domain.models.ResultModel

interface ResultsRepository {
    fun getAllResult(): List<ResultModel>
    suspend fun saveResult(result: ResultModel): Boolean
    suspend fun deleteResult(result: ResultModel)
}