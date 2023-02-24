package com.example.acronymsample.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronymsample.model.MeaningsListModel
import com.example.acronymsample.repository.AcronymRepository
import com.example.acronymsample.repository.NetworkState
import com.example.acronymsample.network.ApiInterface
import com.example.acronymsample.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 *  This viewmodel class has all logic to get meaning of acronym.
 */
class AcronymViewModel : ViewModel() {


    // retrofit instance
    private val retrofitClient by lazy { ApiInterface.getInstance() }
    // repository instance
    private val acronymRepository by lazy { AcronymRepository(retrofitClient) }


    val meaningList = MutableLiveData<List<String>>()
    // loader flag to handle loader
    val isLoading = MutableLiveData(View.GONE)
    //recyclerView Visibility
    val isRvVisible = MutableLiveData(View.GONE)

    private val _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    /**
     *API call to fetch meanings data for acronym provided by user.
     *@param acronym:entered value by user
     */
    fun getAcronymMeaningsData(acronym: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(View.VISIBLE)
            try {
                when (val response = acronymRepository.getMeaningsData(acronym)) {
                    is NetworkState.Success -> {
                        getLargeFormsList(response.data)
                        isLoading.postValue(View.GONE)
                    }
                    is NetworkState.Error -> {
                        onError(response.toString())
                    }
                }
            } catch (ex: UnknownHostException) {
                onError(Util.NETWORK_ERROR_MESSAGE)
            } catch (ex: java.lang.Exception) {
                onError(ex.stackTraceToString())
            }
        }
    }

    /**
     *  Map data from MeaningsData response.
     * @param meaningsListModel:list of meanings response
     */

    private fun getLargeFormsList(meaningsListModel: MeaningsListModel) {
        if ((meaningsListModel.isNotEmpty()) && (meaningsListModel[0].lfs.isNotEmpty())) {
            val lfList = mutableListOf<String>()
            for (lfItem in meaningsListModel[0].lfs) {
                lfList.add(lfItem.lf)
            }
            meaningList.postValue(lfList)
        } else {
            onError(Util.RESPONSE_ERROR_MESSAGE)
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        isLoading.postValue(View.GONE)
    }
}