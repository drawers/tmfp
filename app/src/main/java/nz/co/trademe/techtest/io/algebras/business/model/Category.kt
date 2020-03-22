package nz.co.trademe.techtest.io.algebras.business.model

data class Category(
    val id: String,

    /** The name of the category. */
    val name: String,

    /** Indicates whether the category is a leaf category (i.e. has no children). */
    val isLeaf: Boolean,

    /** The list of subcategories belonging to this category.  */
    var subcategories: List<Category> = emptyList()
)