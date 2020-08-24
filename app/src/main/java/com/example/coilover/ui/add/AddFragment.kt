package com.example.coilover.ui.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coilover.*
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.DateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var addViewModel: AddViewModel
    private lateinit var timestamp: Date
    private lateinit var type: EventType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTimeStamp(Date())
        initEventTypeInput()
        initDateInput()
        setSaveAction()
    }

    private fun setSaveAction() {
        saveButton.setOnClickListener { view ->
            addViewModel.insert(
                Event(
                    timestamp,
                    getInt(odometerInput.text.toString()),
                    getDouble(priceInput.text.toString()),
                    getDouble(amountInput.text.toString()),
                    type
                )
            )
            findNavController().navigate(R.id.navigation_home)
            hideKeyboard(view)
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initDateInput() {
        dateInput.setOnClickListener { _ ->
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.show(childFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                setTimeStamp(Date(it))
            }
        }
    }

    private fun initEventTypeInput() {
        typeInput.setOnItemClickListener { _, _, _, id ->
            type = EventTypeConverters.intToType(id.toInt())
        }

        val adapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropdown_menu_item,
                resources.getStringArray(R.array.event_type_strings)
            )
        }
        typeInput.setAdapter(adapter)
    }

    private fun setTimeStamp(date: Date) {
        timestamp = date
        dateInput.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(timestamp))
    }

    private fun getInt(str: String): Int {
        return if (str.isNotEmpty()) str.toInt() else 0
    }

    private fun getDouble(str: String): Double {
        return if (str.isNotEmpty()) str.toDouble() else 0.0
    }
}