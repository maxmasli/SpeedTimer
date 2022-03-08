package masli.prof.data.repositories

import android.content.Context
import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.repositories.ThemeRepository

private const val THEME_SHARED_PREFERENCES = "theme_shared_preferences"

private const val THEME_VALUE = "theme_value"

class ThemeRepositoryImpl(context: Context) : ThemeRepository {

    private val sharedPrefs = context.getSharedPreferences(THEME_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun getTheme(): ThemeEnum {
        return when(sharedPrefs.getInt(THEME_VALUE, 0)) {
            ThemeEnum.DefaultTheme.value -> ThemeEnum.DefaultTheme
            ThemeEnum.GreenNeonTheme.value -> ThemeEnum.GreenNeonTheme
            ThemeEnum.GreenYellowNeonTheme.value -> ThemeEnum.GreenYellowNeonTheme
            ThemeEnum.HoneyTheme.value -> ThemeEnum.HoneyTheme
            else -> throw IllegalArgumentException("Wrong theme value")
        }
    }

    override fun setTheme(themeEnum: ThemeEnum) {
        sharedPrefs.edit().putInt(THEME_VALUE, themeEnum.value).apply()
    }
}