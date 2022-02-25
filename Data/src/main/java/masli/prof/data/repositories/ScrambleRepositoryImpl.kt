package masli.prof.data.repositories

import masli.prof.domain.repositories.ScrambleRepository

class ScrambleRepositoryImpl : ScrambleRepository {
    override fun getScramble(): String {
        return "R U R' U'"
    }
}