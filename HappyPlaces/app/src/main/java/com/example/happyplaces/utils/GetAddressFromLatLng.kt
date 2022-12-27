package com.example.happyplaces.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import java.util.*

class GetAddressFromLatLng(context: Context,
                            private val latitude: Double,
                            private val longitude: Double) {

    interface AddressListener{
        fun onAddressFound(address:String)
        fun onError()
    }

    fun setCustomAddressListener(addressListener: AddressListener){
        this.mAddressListener = addressListener
    }

    private val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
    private lateinit var mAddressListener: AddressListener

    suspend fun launchBackgroundProcessForRequest() {
        val address = getAddress()

        withContext(Main) {
            if(address.isEmpty()){
                mAddressListener.onError()
            } else {
                mAddressListener.onAddressFound(address)
            }
        }

    }

    private suspend fun getAddress():String {
        try {
            val addressList:List<Address>? = geocoder.getFromLocation(latitude,longitude,1)
            if(!addressList.isNullOrEmpty()) {
                val address:Address = addressList[0]
                val sb = StringBuilder()
                for(i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i) + " ")
                }
                sb.deleteCharAt(sb.length - 1)

                return sb.toString()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return ""
    }



}