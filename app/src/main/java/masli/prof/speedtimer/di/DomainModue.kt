package masli.prof.speedtimer.di

import masli.prof.domain.usecases.DeleteResultUseCase
import masli.prof.domain.usecases.GetAllResultsUseCase
import masli.prof.domain.usecases.GetScrambleUseCase
import masli.prof.domain.usecases.SaveResultUseCase
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

}