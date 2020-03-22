package nz.co.trademe.techtest.io.algebras.ui.adapter

class CategoryPresenter(val callback: (String) -> Unit) {

    fun onClick(id: String) {
        callback(id)
    }
}