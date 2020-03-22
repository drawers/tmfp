package nz.co.trademe.techtest.io.algebras.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.yelp.android.bento.core.ComponentViewHolder
import com.yelp.android.bento.utils.inflate
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.io.algebras.ui.model.CategoryViewState

class CategoryViewHolder : ComponentViewHolder<CategoryPresenter, CategoryViewState>() {

    private lateinit var item: ConstraintLayout
    private lateinit var name: TextView
    private lateinit var chevron: ImageView

    override fun inflate(parent: ViewGroup): View =
        parent.inflate<ConstraintLayout>(R.layout.item_category).apply {
            item = findViewById(R.id.categoryConstraintLayout)
            name = findViewById(R.id.categoryNameTextView)
            chevron = findViewById(R.id.chevronImageView)
        }

    override fun bind(presenter: CategoryPresenter, element: CategoryViewState) {
        item.setOnClickListener { presenter.callback(element.id) }
        name.text = element.name
        chevron.isGone = element.showChevron
    }
}