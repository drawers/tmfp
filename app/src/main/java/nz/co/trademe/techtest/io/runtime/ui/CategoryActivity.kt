package nz.co.trademe.techtest.io.runtime.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yelp.android.bento.componentcontrollers.RecyclerViewComponentController
import com.yelp.android.bento.components.ListComponent
import kotlinx.android.synthetic.main.activity_category.*
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.io.algebras.ui.adapter.CategoryViewHolder
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState

class CategoryActivity : AppCompatActivity() {

    private val componentController by lazy {
        RecyclerViewComponentController(categoryRecyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

    }

    override fun onStart() {
        super.onStart()
        val categories = listOf(
            CategoryViewState(
                "0",
                "hello",
                false
            ),
            CategoryViewState(
                "1",
                "hello",
                true
            )
        )
        val component = ListComponent<Nothing?, CategoryViewState>(null, CategoryViewHolder::class.java)
        component.setData(categories)
        componentController.addComponent(component)
    }
}