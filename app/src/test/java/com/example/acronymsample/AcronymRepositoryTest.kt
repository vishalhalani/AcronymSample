package com.example.acronymsample


import com.example.acronymsample.model.MeaningsListModel
import com.example.acronymsample.network.ApiInterface
import com.example.acronymsample.repository.AcronymRepository
import com.example.acronymsample.repository.NetworkState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class AcronymRepositoryTest {

    private lateinit var acronymRepository: AcronymRepository

    @Mock
    lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        acronymRepository = AcronymRepository(apiInterface)
    }

    @Test
    fun `get meanings data test`() {
        runBlocking {
            val meaningsData = MeaningsListModel()
            Mockito.`when`(apiInterface.getMeaningsData("sf")).thenReturn(
                Response.success(meaningsData)
            )

            val response = acronymRepository.getMeaningsData("sf")
            assertEquals(NetworkState.Success(meaningsData), response)
        }
    }
}