package com.stslex.atten.core.paging

data class PagingResponse<out Value : PagingItem>(
    val items: List<Value>,
)