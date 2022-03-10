package masli.prof.speedtimer.di

import masli.prof.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory<GetScrambleUseCase> {
        GetScrambleUseCase(scrambleRepository = get())
    }

    factory<SaveResultUseCase> {
        SaveResultUseCase(resultsRepository = get())
    }

    factory<GetAllResultsUseCase> {
        GetAllResultsUseCase(resultsRepository = get())
    }

    factory<DeleteResultUseCase> {
        DeleteResultUseCase(resultsRepository = get())
    }

    factory<UpdateResultUseCase> {
        UpdateResultUseCase(resultsRepository = get())
    }

    factory<GetAvgByEventUseCase> {
        GetAvgByEventUseCase(resultsRepository = get())
    }

    factory <GetThemeUseCase>{
        GetThemeUseCase(sharedPrefsRepository = get())
    }

    factory<SetThemeUseCase> {
        SetThemeUseCase(sharedPrefsRepository = get())
    }

    factory<SetDelayUseCase> {
        SetDelayUseCase(sharedPrefsRepository = get())
    }
    factory<GetDelayUseCase> {
        GetDelayUseCase(sharedPrefsRepository = get())
    }

}