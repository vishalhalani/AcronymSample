package com.example.acronymsample.repository

import com.example.acronymsample.model.MeaningsListModel
import com.example.acronymsample.network.ApiInterface

/**
 * This class is used for calling web API or local database data fetching,
 * @param retrofitClient:instance of retrofit client to access api call
 */
class AcronymRepository constructor(private val retrofitClient: ApiInterface) {

    /**
     * Make Api call for acronym provided by user.
     * @param acronym:sort form entered by user
     */
    suspend fun getMeaningsData(acronym: String): NetworkState<MeaningsListModel> {
        val response = retrofitClient.getMeaningsData(acronym)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}