package by.evisun.goldenlime

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class GoldenLimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            fragmentFactory()
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@GoldenLimeApplication)
            // Load modules
            modules(KoinAppModules.create())
        }
    }
}