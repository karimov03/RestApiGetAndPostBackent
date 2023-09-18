package com.example.restapigetpostbackent.Retrofit
import com.example.restapigetpostbackent.models.Contact
import com.example.restapigetpostbackent.models.Contacts
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServis {
    @GET("contacts")
    fun getApi(): Call<List<Contacts>>

    @POST("contacts/")
    fun postApi(@Body contact: Contact): Call<Contact>
}
