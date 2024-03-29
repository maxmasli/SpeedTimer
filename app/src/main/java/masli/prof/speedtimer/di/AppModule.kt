package masli.prof.speedtimer.di

import masli.prof.speedtimer.presentation.screens.resultsscreen.ResultsViewModel
import masli.prof.speedtimer.presentation.screens.settingsscreen.SettingsViewModel
import masli.prof.speedtimer.presentation.screens.timerscreen.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<TimerViewModel> {
        TimerViewModel(
            getScrambleUseCase = get(),
            saveResultUseCase = get(),
            getAllResultsUseCase = get(),
            deleteResultUseCase = get(),
            updateResultUseCase = get(),
            getAvgByEventUseCase = get(),
            getThemeUseCase = get(),
            getDelayUseCase = get()
        )
    }

    viewModel<ResultsViewModel> {
        ResultsViewModel(
            getAllResultsUseCase = get(),
            updateResultUseCase = get(),
            deleteResultUseCase = get(),
            getAvgByEventUseCase = get()
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(setThemeUseCase = get(), setDelayUseCase = get())
    }
}