package masli.prof.domain.repositories

import masli.prof.domain.enums.ThemeEnum

interface SharedPrefsRepository {
    fun getTheme(): ThemeEnum
    fun setTheme(themeEnum: ThemeEnum)
    fun getDelay(): Long
    fun setDelay(delay: Long)
}