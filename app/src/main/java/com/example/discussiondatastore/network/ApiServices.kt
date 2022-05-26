package com.example.discussiondatastore.network

import com.example.discussiondatastore.model.GetAllUserResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {
    @GET("user")
    fun getAllUser() : Call<List<GetAllUserResponseItem>>
}