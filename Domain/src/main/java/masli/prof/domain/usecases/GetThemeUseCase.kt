package masli.prof.domain.usecases

import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.repositories.ThemeRepository

class GetThemeUseCase(private val themeRepository: ThemeRepository) {
    fun execute(): ThemeEnum {
        return themeRepository.getTheme()
    }
}