package com.example.kotlinbasics

fun main() {
    var result = calculateAverage(10,2);
    print(result);
}

fun calculateAverage(a:Int,b:Int) : Double{
    var result = a / b;
    return (a / b).toDouble();
}