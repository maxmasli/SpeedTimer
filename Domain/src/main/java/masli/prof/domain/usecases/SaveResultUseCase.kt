package masli.prof.domain.usecases

import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class SaveResultUseCase(private val resultsRepository: ResultsRepository) {
    suspend fun execute(result: ResultModel): Boolean {
        return resultsRepository.saveResult(result)
    }
}