package com.app.celda.Activities



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.celda.Adapter.CourseAdapter
import com.app.celda.Model.CourseModel
import com.app.celda.Post.APIService
import com.app.celda.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.search_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class SearchFragment: Fragment(), CourseAdapter.Listener {



    private var adapter: CourseAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.search_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CourseAdapter(this)
        recycler_search_view.setHasFixedSize(true)
        recycler_search_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_search_view.adapter = adapter

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard

                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the characters
                //filterList(p0)
                adapter?.clearList()

                val jsonObject = JSONObject()
                jsonObject.put("name", p0)

                val jsonObjectString = jsonObject.toString()
                val rb = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                get(rb)
                recycler_search_view.adapter = adapter
                return true
            }
        })




        recycler_search_view.visibility = View.INVISIBLE
        tvNotFound.setText(R.string.start_search_txt)
        ivStartFound.setImageResource(R.drawable.ic_start_search)
        tvNotFound.visibility = View.VISIBLE
        ivStartFound.visibility = View.VISIBLE

    }


    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun get(rb: RequestBody) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSearch(rb)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                        val lst = Gson().fromJson<List<CourseModel>>(prettyJson)
                        if (lst.isEmpty()) {
                            recycler_search_view.visibility = View.INVISIBLE
                            tvNotFound.setText(R.string.not_found_txt)
                            ivStartFound.setImageResource(R.drawable.ic_what)
                            tvNotFound.visibility = View.VISIBLE
                            ivStartFound.visibility = View.VISIBLE
                        } else {
                            tvNotFound.visibility = View.INVISIBLE
                            ivStartFound.visibility = View.INVISIBLE
                            lst?.forEach { adapter?.addItem(it) }
                            recycler_search_view.visibility = View.VISIBLE
                        }

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), CourseScreen::class.java)
        intent.putExtra("key", "$id")
        startActivity(intent)
    }

}

// Toast.makeText(requireContext(), "Pusto", Toast.LENGTH_LONG).show()