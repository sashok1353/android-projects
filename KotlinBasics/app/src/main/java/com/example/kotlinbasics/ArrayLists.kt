package com.example.kotlinbasics

fun main() {
    val arrayList = arrayListOf<Double>(12.3,14.4,13.2,16.4,12.9)
    var average: Double = 0.0
    for(number in arrayList) {
        average += number
    }

    println(average / arrayList.size)
}