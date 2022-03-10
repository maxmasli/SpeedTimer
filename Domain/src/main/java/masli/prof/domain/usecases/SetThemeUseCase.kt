package masli.prof.domain.usecases

import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.repositories.SharedPrefsRepository

class SetThemeUseCase(private val sharedPrefsRepository: SharedPrefsRepository) {
    fun execute(theme: ThemeEnum) {
        sharedPrefsRepository.setTheme(theme)
    }
}