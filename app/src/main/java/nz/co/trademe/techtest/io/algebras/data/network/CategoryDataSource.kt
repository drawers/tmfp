package nz.co.trademe.techtest.io.algebras.data.network

import arrow.Kind
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.data.network.mapper.WrapperCategory
import nz.co.trademe.techtest.io.algebras.data.network.mapper.normalizeError
import nz.co.trademe.techtest.io.algebras.data.network.mapper.toDomain
import nz.co.trademe.techtest.io.algebras.data.network.mapper.toNetworkError
import nz.co.trademe.techtest.io.runtime.context.Runtime
import retrofit2.Response

fun <F> Runtime<F>.loadCategories(): Kind<F, List<Category>> = fx.concurrent {
    val response = !effect(context.bgDispatcher) { fetchCategories() }
    continueOn(context.mainDispatcher)

    if (response.isSuccessful) {
        response.subcategories()!!.map { it.toDomain() }
    } else {
        !raiseError<List<Category>>(response.code().toNetworkError())
    }
}.handleErrorWith { error -> raiseError(error.normalizeError()) }

private fun <F> Runtime<F>.fetchCategories() = context.tradeMeApi.get().getCategory("0").execute()

private fun Response<WrapperCategory>.subcategories() = body()!!.subcategories