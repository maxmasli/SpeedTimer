package masli.prof.domain.usecases

import masli.prof.domain.enums.EventEnum
import masli.prof.domain.repositories.ScrambleRepository

class GetScrambleUseCase(private val scrambleRepository: ScrambleRepository) {
    fun execute(event: EventEnum): String {
        return scrambleRepository.getScramble(event)
    }
}