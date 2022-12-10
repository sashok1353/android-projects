package com.example.kotlinbasics


fun main() {
    for(num in 0..10000) {
        if(num == 9001) {
            println("IT'S OVER 9000!!!")
        }
    }

    var humidityLevel = 80
    var humidity = "humid"
    while(humidity == "humid") {
        if(humidityLevel < 60) {
            println("it's comfy now")
            humidity = "comfy"
        }
        humidityLevel -= 5
        println("humidity decreased")
    }
}