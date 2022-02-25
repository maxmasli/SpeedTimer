package masli.prof.domain.usecases

import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class GetAllResultsUseCase(private val resultsRepository: ResultsRepository) {
    fun execute(): List<ResultModel> {
        return resultsRepository.getAllResult()
    }
}