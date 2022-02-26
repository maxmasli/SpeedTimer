package masli.prof.data.repositories

import masli.prof.data.storage.ResultStorage
import masli.prof.data.storage.models.ResultDataModel
import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class ResultsRepositoryImpl(private val resultStorage: ResultStorage) : ResultsRepository {
    override fun getAllResult(): List<ResultModel> {
        return resultStorage.allNotes.map { resultData -> mapToDomain(resultData) }
    }

    override suspend fun saveResult(result: ResultModel): Boolean {
        val resultData = mapToData(result)
        resultStorage.insertResultData(resultData)
        return true
    }

    override suspend fun deleteResult(result: ResultModel) {
        resultStorage.deleteResultData(mapToData(result))
    }

    private fun mapToData(result: ResultModel): ResultDataModel {
        return ResultDataModel(
            id = result.id,
            scramble = result.scramble,
            time = result.time,
            description = result.description,
            isDNF = result.isDNF,
            isPlus = result.isPlus
        )
    }

    private fun mapToDomain(resultData: ResultDataModel): ResultModel {
        return ResultModel(
            id = resultData.id,
            scramble = resultData.scramble,
            time = resultData.time,
            description = resultData.description,
            isDNF = resultData.isDNF,
            isPlus = resultData.isPlus
        )
    }
}