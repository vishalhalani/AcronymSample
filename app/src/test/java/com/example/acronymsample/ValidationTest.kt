package com.example.acronymsample

import com.example.acronymsample.utils.Util
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilTest {

    @Test
    fun validateAcronymTest() {
        val acronym = "HMM"
        assertEquals(true, Util.isValid(acronym).first)
    }

    @Test
    fun validateEmptyAcronymTest() {
        val acronym = ""
        assertEquals(false, Util.isValid(acronym).first)
    }

    @Test
    fun validateSingleCharAcronymTest() {
        val acronym = "a"
        assertEquals(false, Util.isValid(acronym).first)
    }

    @Test
    fun validateSpecialCharAcronymTest() {
        val acronym = "a@a"
        assertEquals(false, Util.isValid(acronym).first)
    }
}