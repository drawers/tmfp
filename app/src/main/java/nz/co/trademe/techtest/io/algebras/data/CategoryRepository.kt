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
        NetworkFirst -> loadCategories(mcat) // TODO change to conditional call
        LocalOnly -> loadCategories(mcat) // TODO change to local only cache call
        LocalFirst -> loadCategories(mcat) // TODO change to conditional call
    }

//fun <F> Runtime<F>.getRootCategories(policy: CachePolicy, mcat: String): Kind<F, List<Category>> =
//    when (policy) {
//        NetworkOnly -> loadRootCategories()
//        NetworkFirst -> loadCategories(mcat) // TODO change to conditional call
//        LocalOnly -> loadCategories(mcat) // TODO change to local only cache call
//        LocalFirst -> loadCategories(mcat) // TODO change to conditional call
//    }

//fun <F> Runtime<F>.getNewsItemDetailsWithCachePolicy(policy: CachePolicy, title: String): Kind<F, NewsItem> =
//    when (policy) {
//        NetworkOnly -> loadCategories(id)
//        NetworkFirst -> loadCategories(id) // TODO change to conditional call
//        LocalOnly -> loadCategories(id) // TODO change to local only cache call
//        LocalFirst -> loadCategories(id) // TODO change to conditional call
//    }
