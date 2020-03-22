package nz.co.trademe.techtest.io.algebras.business

import arrow.Kind
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.data.CachePolicy
import nz.co.trademe.techtest.io.algebras.data.getCategories
import nz.co.trademe.techtest.io.runtime.context.Runtime

fun <F> Runtime<F>.getCategories(mcat: String?): Kind<F, List<Category>> =
    getCategories(CachePolicy.NetworkOnly, mcat)
