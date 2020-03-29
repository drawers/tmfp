package nz.co.trademe.techtest.io.algebras

import arrow.Kind
import nz.co.trademe.techtest.io.algebras.business.getCategories
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.business.model.DomainError
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState
import nz.co.trademe.techtest.io.runtime.context.Runtime
import nz.co.trademe.techtest.io.runtime.context.State

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

fun <F> Runtime<F>.getCategoriesForView(mcat: String? = null, view: CategoriesListView): Kind<F, Unit> {
    val runtime = this
    return fx.concurrent {
        !effect { view.showLoading() }
        val maybeCategories = !getCategories(mcat).attempt()
        !effect { view.hideLoading() }
        !effect {
            maybeCategories.fold(
                ifLeft = {
                    displayErrors(view, it)
                },
                ifRight = {
                    runtime.context.state = State(mcat)
                    view.renderCategories(it.map { category -> category.toViewState() })
                }
            )
        }
    }
}

fun <F> Runtime<F>.getPreviousCategory(view: CategoriesListView): Kind<F, Unit> {
    val runtime = this
    val mcat = previousCategory(runtime.context.state.category)
    return fx.concurrent {
        !effect { view.showLoading() }
        val maybeCategories = !getCategories(mcat).attempt()
        !effect { view.hideLoading() }
        !effect {
            maybeCategories.fold(
                ifLeft = {
                    displayErrors(view, it)
                },
                ifRight = {
                    runtime.context.state = State(mcat)
                    view.renderCategories(it.map { category -> category.toViewState() })
                }
            )
        }
    }
}


private fun Category.toViewState() = CategoryViewState(
    id = id,
    name = name,
    showChevron = isLeaf
)

private fun previousCategory(mcat: String?): String? {
    mcat ?: return null

    val parts = mcat.split("-").dropLastWhile { it.isEmpty() }
    return when {
        parts.size < 2 -> null
        else -> parts.penultimate()
    }
}

private fun <T> List<T>.penultimate() = dropLast(1).last()