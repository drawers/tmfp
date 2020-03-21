package nz.co.trademe.techtest.io.algebras.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.yelp.android.bento.core.ComponentViewHolder
import com.yelp.android.bento.utils.inflate
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState

class CategoryViewHolder : ComponentViewHolder<Nothing?, CategoryViewState>() {

    private lateinit var name: TextView
    private lateinit var isLeaf: TextView

    override fun inflate(parent: ViewGroup): View =
        parent.inflate<ConstraintLayout>(R.layout.item_category).apply {
            name = findViewById(R.id.categoryNameTextView)
            isLeaf = findViewById(R.id.categoryLeafTextView)
        }

    override fun bind(presenter: Nothing?, element: CategoryViewState) {
        name.text = element.name
        isLeaf.text = element.isLeaf.toString()
    }
}