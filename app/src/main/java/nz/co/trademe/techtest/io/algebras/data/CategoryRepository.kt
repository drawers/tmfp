package nz.co.trademe.techtest.io.algebras.data

import arrow.Kind
import nz.co.trademe.techtest.io.algebras.data.CachePolicy.*
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.data.network.loadCategories
import nz.co.trademe.techtest.io.runtime.context.Runtime

sealed class CachePolicy {
    object NetworkOnly : CachePolicy()
    object NetworkFirst : CachePolicy()
    object LocalOnly : CachePolicy()
    object LocalFirst : CachePolicy()
}

fun <F> Runtime<F>.getCategories(policy: CachePolicy, mcat: String?): Kind<F, List<Category>> =
    when (policy) {
        NetworkOnly -> loadCategories(mcat)
        NetworkFirst -> loadCategories(mcat)
        LocalOnly -> loadCategories(mcat)
        LocalFirst -> loadCategories(mcat)
    }