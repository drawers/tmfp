package nz.co.trademe.techtest.io.algebras

import arrow.Kind
import arrow.core.Either
import nz.co.trademe.techtest.io.algebras.business.getCategories
import nz.co.trademe.techtest.io.algebras.business.model.Category
import nz.co.trademe.techtest.io.algebras.business.model.DomainError
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState
import nz.co.trademe.techtest.io.runtime.context.Runtime

typealias MaybeCategories = Either<Throwable, List<Category>>

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

fun <F> Runtime<F>.currentCategoriesForView(view: CategoriesListView): Kind<F, Unit> {
    val state = context.state
    return fx.concurrent {
        !specifiedCategoriesForView(state.category, view)
    }
}

fun <F> Runtime<F>.previousCategoriesForView(view: CategoriesListView): Kind<F, Unit> {
    val state = context.state
    return fx.concurrent {
        !specifiedCategoriesForView(state.previousCategory, view)
    }
}

fun <F> Runtime<F>.specifiedCategoriesForView(mcat: String?, view: CategoriesListView): Kind<F, Unit> {
    return fx.concurrent {
        val maybeCategories = !categoriesForView(mcat, view)
        !effect {
            maybeCategories.fold(
                ifLeft = {
                    displayErrors(view, it)
                },
                ifRight = {
                    view.renderCategories(
                        it.map { category -> category.toViewState() }
                    )
                    updateState(mcat)
                }
            )
        }

    }
}

private fun <F> Runtime<F>.categoriesForView(mcat: String?, view: CategoriesListView): Kind<F, MaybeCategories> {
    return fx.concurrent {
        !effect { view.showLoading() }
        val maybeCategories = !getCategories(mcat).attempt()
        !effect { view.hideLoading() }
        maybeCategories
    }
}

private fun <F> Runtime<F>.updateState(mcat: String?): Kind<F, Unit> {
    val context = this.context
    return fx.concurrent {
        !effect {
            context.state.update(mcat)
        }
    }
}

private fun Category.toViewState() = CategoryViewState(
    id = id,
    name = name,
    showChevron = isLeaf
)

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