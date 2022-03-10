package masli.prof.domain.usecases

import masli.prof.domain.repositories.SharedPrefsRepository

class SetDelayUseCase(private val sharedPrefsRepository: SharedPrefsRepository) {
    fun execute(delay: Long) {
        sharedPrefsRepository.setDelay(delay)
    }
}