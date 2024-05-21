package com.stslex.atten

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform