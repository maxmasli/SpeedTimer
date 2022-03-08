package masli.prof.domain.repositories

import masli.prof.domain.enums.ThemeEnum

interface ThemeRepository {
    fun getTheme(): ThemeEnum
    fun setTheme(themeEnum: ThemeEnum)
}