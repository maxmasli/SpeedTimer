package masli.prof.speedtimer.di

import masli.prof.speedtimer.presentation.screens.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        TimerViewModel(
            getScrambleUseCase = get(),
            saveResultUseCase = get(),
            getAllResultsUseCase = get(),
            deleteResultUseCase = get(),
            updateResultUseCase = get()
        )
    }
}