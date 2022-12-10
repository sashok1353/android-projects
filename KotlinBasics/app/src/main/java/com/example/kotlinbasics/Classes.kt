package com.example.kotlinbasics

fun main() {
    MobilePhone("Android", "Samsung","Galaxy S20 Ultra")
    MobilePhone("Android", "Samsung","Galaxy S10")
    var iphone = MobilePhone("IOS", "Apple","Iphone 14 Pro Max")
    iphone.chargeBattery(30);
}

class MobilePhone(osName: String, brand: String, model: String) {

    private var battery: Int = 20;

    init{
        println("Here the osName is $osName, brand is $brand, model is $model")
    }

    fun chargeBattery(value: Int) {
        println("The phone had $battery%")
        this.battery += value;
        println("The phone was charged by $value")
        println("The phone has $battery%")
    }
}