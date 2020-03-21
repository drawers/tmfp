package nz.co.trademe.techtest.io.runtime.context

import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.io.concurrent.concurrent

fun IO.Companion.runtime(ctx: RuntimeContext) = object : Runtime<ForIO>(
    IO.concurrent(), ctx) {}