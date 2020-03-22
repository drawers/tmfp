package nz.co.trademe.techtest.io.runtime.context

import arrow.fx.typeclasses.Concurrent
import kotlinx.coroutines.CoroutineDispatcher
import nz.co.trademe.wrapper.TradeMeApi

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
abstract class Runtime<F>(
    concurrent: Concurrent<F>, //async data type can be started concurrently and cancelled
    val context: RuntimeContext
) : Concurrent<F> by concurrent

data class RuntimeContext(
    val bgDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher,
    val tradeMeApi: TradeMeApi
)