package nz.co.trademe.techtest.io.runtime.context

import arrow.fx.rx2.ForSingleK
import arrow.fx.rx2.SingleK
import arrow.fx.rx2.extensions.asCoroutineContext
import arrow.fx.rx2.extensions.concurrent
import arrow.fx.typeclasses.Dispatchers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.CoroutineContext

fun SingleK.Companion.runtime(ctx: RuntimeContext) = object : Runtime<ForSingleK>(
    SingleK.concurrent(
        object : Dispatchers<ForSingleK> {
            override fun default(): CoroutineContext = Schedulers.computation().asCoroutineContext()

            override fun io(): CoroutineContext = Schedulers.io().asCoroutineContext()
        }
    ), ctx
) {}