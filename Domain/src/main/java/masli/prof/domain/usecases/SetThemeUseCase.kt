package masli.prof.domain.usecases

import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.repositories.ThemeRepository

class SetThemeUseCase(private val themeRepository: ThemeRepository) {
    fun execute(theme: ThemeEnum) {
        themeRepository.setTheme(theme)
    }
}