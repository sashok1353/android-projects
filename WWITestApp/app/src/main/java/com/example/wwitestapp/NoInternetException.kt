package com.example.wwitestapp 

class NoInternetException : RuntimeException {
    constructor() : super()
    constructor(cause: Throwable?) : super(cause)
}