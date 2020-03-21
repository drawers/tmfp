package nz.co.trademe.techtest.io.algebras.data.network.mapper

import nz.co.trademe.techtest.io.algebras.business.model.Category

typealias WrapperCategory = nz.co.trademe.wrapper.models.Category

fun WrapperCategory.toDomain(): Category =
    Category(
        id = id,
        name = name,
        isLeaf = isLeaf,
        subcategories = this.subcategories?.map(WrapperCategory::toDomain) ?: kotlin.collections.emptyList()
    )