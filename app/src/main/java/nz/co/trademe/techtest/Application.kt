package nz.co.trademe.techtest

import android.app.Application
import android.content.Context
import kotlinx.coroutines.Dispatchers
import nz.co.trademe.techtest.io.runtime.context.State
import nz.co.trademe.techtest.io.runtime.context.RuntimeContext
import nz.co.trademe.wrapper.TradeMeApi

class TmApplication : Application() {

    val runtimeContext by lazy {
        RuntimeContext(
            bgDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            tradeMeApi = TradeMeApi(),
            state = State()
        )
    }
}

fun Context.tmApp(): TmApplication = applicationContext as TmApplication