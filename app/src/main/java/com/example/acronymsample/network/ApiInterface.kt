package com.example.acronymsample.network

import com.example.acronymsample.model.MeaningsListModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This is ApiInterface, which provides Retrofit Client to call web API.
 */
interface ApiInterface {

    /**
     * Get full meaning data from api using acronym entered by user
     * @param acronym: user entered value
     */
    @GET("dictionary.py")
    suspend fun getMeaningsData(@Query("sf") acronym: String): Response<MeaningsListModel>

    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"
        private var retrofitService: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiInterface::class.java)
            }
            return retrofitService!!
        }
    }
}