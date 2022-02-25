package masli.prof.speedtimer.app

import android.app.Application
import masli.prof.speedtimer.di.appModule
import masli.prof.speedtimer.di.dataModule
import masli.prof.speedtimer.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}