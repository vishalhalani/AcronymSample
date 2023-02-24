package com.example.acronymsample.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.acronymsample.R
import com.example.acronymsample.databinding.ActivityMainBinding
import com.example.acronymsample.utils.Util
import com.example.acronymsample.viewmodel.AcronymViewModel


class MainActivity : AppCompatActivity() {
    // Initialize variables
    private lateinit var viewModel: AcronymViewModel
    private val adapter = LfListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Assign variable
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(
                this,
            )[AcronymViewModel::class.java]

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.meaningList.observe(this) {
            adapter.setLfList(it)
            viewModel.isRvVisible.postValue(View.VISIBLE)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        binding.activityMainSearchEt.addTextChangedListener (object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                        if (!s.isNullOrEmpty()) {
                            binding.activityMainSearchTil.error=null
                            val isValidAbbreviation = Util.isValid(s.toString())
                            if (!isValidAbbreviation.first){
                                binding.activityMainSearchTil.error=isValidAbbreviation.second
                            }else{
                                binding.activityMainSearchTil.error=null
                            }
                        } else  binding.activityMainSearchTil.error=null

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.activityMainFindBtn.setOnClickListener {
            binding.activityMainSearchTil.error=null
            binding.activityMainSearchEt.hideKeyboard()
            val acronym = binding.activityMainSearchEt.text.toString()
            val isValidAbbreviation = Util.isValid(acronym)

            when {
                !isValidAbbreviation.first -> {
                    binding.activityMainSearchTil.error=isValidAbbreviation.second

                }
                else -> {
                    viewModel.getAcronymMeaningsData(acronym)
                    binding.recyclerview.smoothScrollToPosition(0)
                }
            }
        }
        binding.activityMainSearchTil.setEndIconOnClickListener {
            binding.activityMainSearchTil.error=null
            binding.activityMainSearchEt.text?.clear()
            viewModel.isRvVisible.postValue(View.GONE)
        }
    }


    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
