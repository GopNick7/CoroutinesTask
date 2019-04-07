package com.nekitapp.coroutinestask.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nekitapp.coroutinestask.R
import com.nekitapp.coroutinestask.data.net.model.InternalData
import kotlinx.android.synthetic.main.item_title.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var internalDataList = mutableListOf<InternalData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false)
        return MainAdapter.MainViewHolder(view)
    }

    override fun getItemCount(): Int = internalDataList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(internalDataList[position])
    }

    fun showAllItems(internalDataList: List<InternalData>) {
        this.internalDataList.clear()
        this.internalDataList.addAll(internalDataList)
        notifyDataSetChanged()
    }

    class MainViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.tv_title

        internal fun bind(internalData: InternalData) {
            title.text = internalData.title
        }
    }
}