package nz.co.trademe.techtest.io.algebras

import arrow.Kind
import nz.co.trademe.techtest.io.algebras.business.getCategories
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.business.model.DomainError
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState
import nz.co.trademe.techtest.io.runtime.context.Runtime

interface CategoriesView {

    fun showLoading(): Unit

    fun hideLoading(): Unit

    fun showNotFoundError(): Unit

    fun showGenericError(): Unit

    fun showAuthenticationError(): Unit
}

interface CategoriesListView : CategoriesView {

    fun renderCategories(categories: List<CategoryViewState>): Unit
}

private fun displayErrors(
    view: CategoriesView,
    t: Throwable
): Unit {
    when (DomainError.fromThrowable(t)) {
        is DomainError.NotFoundError -> view.showNotFoundError()
        is DomainError.UnknownServerError -> view.showGenericError()
        is DomainError.AuthenticationError -> view.showAuthenticationError()
    }
}

fun <F> Runtime<F>.getAllCategories(mcat: String?, view: CategoriesListView): Kind<F, Unit> = fx.concurrent {
    !effect { view.showLoading() }
    val maybeCategories = !getCategories(mcat).attempt()
    !effect { view.hideLoading() }
    !effect {
        maybeCategories.fold(
            ifLeft = { displayErrors(view, it) },
            ifRight = { view.renderCategories(it.map { category -> category.toViewState() }) }
        )
    }
}

fun Category.toViewState() = CategoryViewState(
    id = id,
    name = name,
    showChevron = isLeaf
)