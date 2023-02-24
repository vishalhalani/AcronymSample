package com.example.acronymsample.ui


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.acronymsample.R
import com.example.acronymsample.databinding.ItemMeaningBinding



/**
 * This is ListAdapter class to display list of large forms in recyclerview.
 */
class LfListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var largeFormsList = mutableListOf<String>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMeaningBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_meaning, parent, false
        )


        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val largeForm = largeFormsList[position]
        holder.binding.lfTv.text = largeForm
    }

    override fun getItemCount(): Int {
        return largeFormsList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setLfList(lfs: List<String>) {
        this.largeFormsList = lfs.toMutableList()
        notifyDataSetChanged()
    }
}

class MainViewHolder(val binding: ItemMeaningBinding) : RecyclerView.ViewHolder(binding.root)