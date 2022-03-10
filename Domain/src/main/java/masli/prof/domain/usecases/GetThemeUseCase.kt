package masli.prof.domain.usecases

import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.repositories.SharedPrefsRepository

class GetThemeUseCase(private val sharedPrefsRepository: SharedPrefsRepository) {
    fun execute(): ThemeEnum {
        return sharedPrefsRepository.getTheme()
    }
}