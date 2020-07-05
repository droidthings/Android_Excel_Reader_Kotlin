package com.abi.excelreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ExcelAdapter(private val excelDataList: ArrayList<ExcelData>) : RecyclerView.Adapter<ExcelAdapter.ItemViewHolder>(),
    Filterable {

    var searchableExcelList: ArrayList<ExcelData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return excelDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val currentItem = excelDataList[position]
            holder.mTextName.text = currentItem.name
            holder.mTextLocation.text = currentItem.location
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var mTextName: TextView = itemView.tv_name
         var mTextLocation: TextView = itemView.tv_location
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    searchableExcelList = excelDataList
                } else {
                    val resultList = ArrayList<ExcelData>()
                    for (row in excelDataList) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    searchableExcelList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchableExcelList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchableExcelList = results?.values as ArrayList<ExcelData>
                notifyDataSetChanged()
            }

        }
    }

}