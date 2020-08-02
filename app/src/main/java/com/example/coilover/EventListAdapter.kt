package com.example.coilover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.eventlist_row.view.*


class EventListAdapter() : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    private var items = emptyList<Event>()

    class EventViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val row = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.eventlist_row, parent, false)
        return EventViewHolder(row)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EventViewHolder, index: Int) {
        holder.row.eventListRow.text = items[index].toString()
    }

    fun update(events: List<Event>) {
        this.items = events
        notifyDataSetChanged()
    }
}