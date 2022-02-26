package masli.prof.domain.repositories

import masli.prof.domain.enums.EventEnum

interface ScrambleRepository {
    fun getScramble(event: EventEnum): String
    fun setSeed(seed: Long)
    fun getSeed(): Long
}