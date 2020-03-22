package nz.co.trademe.techtest.io.runtime.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import com.yelp.android.bento.componentcontrollers.RecyclerViewComponentController
import com.yelp.android.bento.components.ListComponent
import kotlinx.android.synthetic.main.activity_category.*
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.io.algebras.CategoriesListView
import nz.co.trademe.techtest.io.algebras.business.getCategories
import nz.co.trademe.techtest.io.algebras.getAllCategories
import nz.co.trademe.techtest.io.algebras.ui.adapter.CategoryPresenter
import nz.co.trademe.techtest.io.algebras.ui.adapter.CategoryViewHolder
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState
import nz.co.trademe.techtest.io.runtime.context.runtime
import nz.co.trademe.techtest.tmApp

class CategoryActivity : AppCompatActivity(), CategoriesListView {

    private val componentController by lazy {
        RecyclerViewComponentController(categoryRecyclerView)
    }

    private val component by lazy {
        ListComponent(CategoryPresenter(callback), CategoryViewHolder::class.java).also {
            componentController.addComponent(it)
        }
    }

    private val callback: (String) -> Unit by lazy {
        { id: String ->
            val context = this
            unsafe {
                runNonBlocking({
                    IO.runtime(context.tmApp().runtimeContext).getAllCategories(id, this@CategoryActivity)
                }, {})
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    override fun onStart() {
        super.onStart()
        val context = this
        unsafe {
            runNonBlocking({
                IO.runtime(context.tmApp().runtimeContext).getAllCategories(null, this@CategoryActivity)
            }, {})
        }
    }

    override fun showLoading() {
        println("Loading")
    }

    override fun hideLoading() {
        println("not loading")
    }

    override fun showNotFoundError() {
        println("not found error")
    }

    override fun showGenericError() {
        println("generic error")
    }

    override fun showAuthenticationError() {
        println("authentication error")
    }

    override fun renderCategories(categories: List<CategoryViewState>) {
        component.setData(categories)
    }
}