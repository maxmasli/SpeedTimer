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

}