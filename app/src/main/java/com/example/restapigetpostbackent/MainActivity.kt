package com.example.restapigetpostbackent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.restapigetpostbackent.Retrofit.ApiClient
import com.example.restapigetpostbackent.Retrofit.ApiServis
import com.example.restapigetpostbackent.databinding.ActivityMainBinding
import com.example.restapigetpostbackent.databinding.ItemDialogBinding
import com.example.restapigetpostbackent.models.Contact
import com.example.restapigetpostbackent.models.Contacts
import com.google.gson.Gson
import com.google.gson.JsonObject
import myAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<Contacts>
    private lateinit var adapter: myAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val alertDialog = AlertDialog.Builder(this)
        val dialogview = ItemDialogBinding.inflate(layoutInflater)
        alertDialog.setView(dialogview.root)
        val dialog = alertDialog.create()

        binding.btnAdd.setOnClickListener {
            dialog.show()
        }

        dialogview.btnSaqlash.setOnClickListener {
            val contact = if (list.isEmpty()) {
                Contact(
                    dialogview.edtName.text.toString(),
                    dialogview.edtNumber.text.toString()
                )
            } else {
                Contact(
                    dialogview.edtName.text.toString(),
                    dialogview.edtNumber.text.toString()
                )
            }


            val api = ApiClient.getResponse().create(ApiServis::class.java)
            api.postApi(contact).enqueue(object : Callback<Contact> {
                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    if (response.isSuccessful) {
                        val returnedContact = response.body()
                        val alertDialog = AlertDialog.Builder(this@MainActivity)
                        val dialogview = ItemDialogBinding.inflate(layoutInflater)
                        alertDialog.setView(dialogview.root)
                        val dialog = alertDialog.create()
                        dialog.hide()
                        // POST so'rovi muvaffaqiyatli yakunlandi
                        // Bu yerda kerakli amallarni bajarishingiz mumkin
                        Toast.makeText(
                            this@MainActivity,
                            "Ma'lumotlar saqlandi",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Xatolik yuz berdi
                        Toast.makeText(
                            this@MainActivity,
                            "API ga POST so'rovni yuborishda xatolik",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    // So'rovni amalga oshirishda xatolik yuz berdi
                    Toast.makeText(
                        this@MainActivity,
                        "Ma'lumotlarni saqlashda xatolik: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        list = ArrayList()
        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchDataAndUpdateUI()
        }

        fetchDataAndUpdateUI()
    }

    private fun fetchDataAndUpdateUI() {
        val api = ApiClient.getResponse().create(ApiServis::class.java)
        list = ArrayList()
        api.getApi().enqueue(object : Callback<List<Contacts>> {
            override fun onResponse(call: Call<List<Contacts>>, response: Response<List<Contacts>>) {
                if (response.isSuccessful) {
                    list = response.body() as ArrayList<Contacts>
                    adapter = myAdapter(list)
                    binding.rv.adapter = adapter
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this@MainActivity, "Ma'lumotlar yuklandi", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Xatolik", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<List<Contacts>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Xatolik", Toast.LENGTH_SHORT).show()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}
