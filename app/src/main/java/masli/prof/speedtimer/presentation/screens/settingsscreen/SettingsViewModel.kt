package masli.prof.speedtimer.presentation.screens.settingsscreen

import androidx.lifecycle.ViewModel
import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.usecases.SetThemeUseCase

class SettingsViewModel(private val setThemeUseCase: SetThemeUseCase): ViewModel(){
    fun setTheme(theme: ThemeEnum) {
        setThemeUseCase.execute(theme)
    }
}