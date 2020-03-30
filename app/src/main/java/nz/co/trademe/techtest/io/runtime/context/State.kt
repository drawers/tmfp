package nz.co.trademe.techtest.io.runtime.context

import java.util.concurrent.atomic.AtomicReference

class State {

    private val categoryInternal: AtomicReference<String?> = AtomicReference(null)

    val category: String?
        get() = categoryInternal.get()

    fun update(mcat: String?) {
        categoryInternal.set(mcat)
    }

    val previousCategory: String?
        get() {
            val cat = category ?: return null

            val parts = cat.split("-").dropLastWhile { it.isEmpty() }
            return when {
                parts.size < 2 -> null
                else -> parts.penultimate()
            }
        }

    override fun toString(): String {
        return "State(category=$category)"
    }

    private fun <T> List<T>.penultimate() = dropLast(1).last()
}
