package com.stslex.atten.core.core.result

fun interface Mapping<in T : Any, R : Any> {

    operator fun invoke(data: T): R
}