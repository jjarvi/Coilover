package com.example.coilover

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import kotlinx.android.synthetic.main.content_new_event_form.*


class NewEventForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event_form)
        val type = intent.extras?.get("type") as EventType

        if (type != EventType.Refuel) {
            amountRow.visibility = View.INVISIBLE
        }

        saveButton.setOnClickListener { view ->
            val model = ViewModelProvider(this).get(EventViewModel::class.java)
            model.insert(Event(
                getInt(odometerInput.text.toString()),
                getDouble(priceInput.text.toString()),
                getDouble(amountInput.text.toString()),
                type))
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun getInt(str: String): Int {
        return if (str.isNotEmpty()) str.toInt() else 0
    }

    private fun getDouble(str: String): Double {
        return if (str.isNotEmpty()) str.toDouble() else 0.0
    }



}
