package com.example.coilover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coilover.ui.add.EventCsvParser
import kotlinx.android.synthetic.main.errorlist_row.view.*


class ErrorListAdapter() : RecyclerView.Adapter<ErrorListAdapter.StringViewHolder>() {

    private var items = emptyList<Pair<EventCsvParser.Status, String>>()

    class StringViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val row = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.errorlist_row, parent, false)
        return StringViewHolder(row)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StringViewHolder, index: Int) {
        holder.row.errorListRow.text = "%d: %s".format(index, items[index].second)
    }

    fun update(strings: List<Pair<EventCsvParser.Status, String>>) {
        this.items = strings
        notifyDataSetChanged()
    }
}