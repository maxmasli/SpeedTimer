package masli.prof.speedtimer.di

import masli.prof.data.repositories.ResultsRepositoryImpl
import masli.prof.data.repositories.ScrambleRepositoryImpl
import masli.prof.data.repositories.SharedPrefsRepositoryImpl
import masli.prof.data.storage.ResultStorage
import masli.prof.data.storage.databasestorage.DatabaseResultStorageImpl
import masli.prof.data.storage.room.dao.ResultDataDao
import masli.prof.data.storage.room.db.DatabaseResult
import masli.prof.domain.repositories.ResultsRepository
import masli.prof.domain.repositories.ScrambleRepository
import masli.prof.domain.repositories.SharedPrefsRepository
import org.koin.dsl.module

val dataModule = module {
    single<ScrambleRepository> {
        ScrambleRepositoryImpl()
    }

    single<ResultsRepository> {
        ResultsRepositoryImpl(resultStorage = get())
    }

    single<ResultStorage> {
        DatabaseResultStorageImpl(resultDataDao = get())
    }

    single<ResultDataDao> {
        DatabaseResult.getInstance(context = get()).getResultDataDao()
    }

    single<SharedPrefsRepository> {
        SharedPrefsRepositoryImpl(context = get())
    }
}