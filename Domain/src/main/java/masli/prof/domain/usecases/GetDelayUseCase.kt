package masli.prof.domain.usecases

import masli.prof.domain.repositories.SharedPrefsRepository

class GetDelayUseCase(private val sharedPrefsRepository: SharedPrefsRepository) {
    fun execute(): Long {
        return sharedPrefsRepository.getDelay()
    }
}