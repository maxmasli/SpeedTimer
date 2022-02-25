package masli.prof.domain.usecases

import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class DeleteResultUseCase(private val resultsRepository: ResultsRepository) {
    suspend fun execute(result: ResultModel) {
        resultsRepository.deleteResult(result)
    }
}