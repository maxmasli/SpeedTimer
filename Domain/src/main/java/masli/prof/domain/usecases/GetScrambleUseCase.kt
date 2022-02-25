package masli.prof.domain.usecases

import masli.prof.domain.repositories.ScrambleRepository

class GetScrambleUseCase(private val scrambleRepository: ScrambleRepository) {
    fun execute(): String {
        return scrambleRepository.getScramble()
    }
}