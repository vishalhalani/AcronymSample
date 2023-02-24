package com.example.acronymsample.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * This class is use for validation and other reusable functions
 */
object Util {
    private const val SUCCESS_MESSAGE = "Yeh!! We found some meanings as per entered acronym"
    private const val EMPTY_SF_MESSAGE = "Please enter valid acronym."
    private const val SINGLE_CHAR_SF_MESSAGE = "Acronym can't be single character."
    private const val NON_ALPHABET_SF_MESSAGE = "Acronym can contain only alphabets."
    const val NETWORK_ERROR_MESSAGE = "Please check Internet Connectivity."
    const val RESPONSE_ERROR_MESSAGE = "Something went wrong."

    /**
     * this function return validation flag with appropriate message
     * @param acronym:user entered value
     */
    fun isValid(acronym: String): Pair<Boolean, String> {
        return if (acronym.isEmpty())
            Pair(false, EMPTY_SF_MESSAGE)
        else if (acronym.length == 1)
            Pair(false, SINGLE_CHAR_SF_MESSAGE)
        else if (!(acronym.matches("^[a-zA-Z]*$".toRegex())))
            Pair(false, NON_ALPHABET_SF_MESSAGE)
        else
            Pair(true, SUCCESS_MESSAGE)
    }

}