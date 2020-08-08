package com.example.coilover

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

import kotlinx.android.synthetic.main.content_new_event.*


class NewEventActivity : AppCompatActivity() {

    private fun onButtonPressed(type: EventType) {
        val intent = Intent(this, NewEventForm::class.java)
        intent.putExtras(bundleOf("type" to type))
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        refuelButton.setOnClickListener { view ->
            onButtonPressed(EventType.Refuel)
        }

        washButton.setOnClickListener { view ->
            onButtonPressed(EventType.Wash)
        }

        serviceButton.setOnClickListener { view ->
            onButtonPressed(EventType.Service)
        }

        productButton.setOnClickListener { view ->
            onButtonPressed(EventType.Product)
        }

        sparePartButton.setOnClickListener { view ->
            onButtonPressed(EventType.SparePart)
        }
    }
}
