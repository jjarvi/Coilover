package com.example.coilover

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.google.android.material.datepicker.MaterialDatePicker

import kotlinx.android.synthetic.main.content_new_event_form.*
import java.text.DateFormat
import java.util.*


class NewEventForm : AppCompatActivity() {

    private lateinit var timestamp: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event_form)

        setTimeStamp(Date())

        val type = intent.extras?.get("type") as EventType
        if (type != EventType.Refuel) {
            amountRow.visibility = View.INVISIBLE
        }

        saveButton.setOnClickListener { view ->
            val model = ViewModelProvider(this).get(EventViewModel::class.java)
            model.insert(Event(
                timestamp,
                getInt(odometerInput.text.toString()),
                getDouble(priceInput.text.toString()),
                getDouble(amountInput.text.toString()),
                type))
            startActivity(Intent(this, MainActivity::class.java))
        }

        dateInput.setOnClickListener { view ->
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                setTimeStamp(Date(it))
            }
        }
    }

    private fun setTimeStamp(date: Date) {
        timestamp = date
        dateInput.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(timestamp)
    }

    private fun getInt(str: String): Int {
        return if (str.isNotEmpty()) str.toInt() else 0
    }

    private fun getDouble(str: String): Double {
        return if (str.isNotEmpty()) str.toDouble() else 0.0
    }
}
