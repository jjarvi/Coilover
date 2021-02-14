package com.example.coilover.ui.add

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coilover.EventListAdapter
import com.example.coilover.R
import com.example.coilover.ErrorListAdapter
import kotlinx.android.synthetic.main.activity_import_csv.*
import kotlinx.android.synthetic.main.import_csv_dialog.view.*


class ImportCsvActivity : AppCompatActivity() {

    private val IMPORT_REQUEST = 0xA
    private lateinit var vm: ImportCsvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_csv)
        vm = ViewModelProvider(this).get(ImportCsvViewModel::class.java)
        importCancelButton.visibility = View.GONE
        importAddToDbButton.visibility = View.GONE
        setupEventList()
        observeImportState()
        showImportFileDialog()
    }

    private fun showImportFileDialog() {
        var intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_OPEN_DOCUMENT)
        startActivityForResult(Intent.createChooser(intent, "Select a file"), IMPORT_REQUEST)
    }

    private fun setupEventList() {
        val adapter = EventListAdapter()
        importedEventsList.adapter = adapter
        importedEventsList.layoutManager = LinearLayoutManager(this)

        vm.events.observe(this, Observer { importedEventsList ->
            importedEventsList?.let {
                adapter.update(it)
            }
        })
    }

    private fun observeImportState() {
        vm.ready.observe(this, Observer {
            if (vm.ready.value == true) {
                importCsvProgressIndicator.hide()
                showImportReadyDialog()
            }
        })
    }

    private fun showImportReadyDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.import_csv_dialog, null)
        view.importReadyText.text = getString(R.string.import_ready_text).format(vm.events.value?.size)
        setupImportErrorInformation(view)
        builder.setPositiveButton(R.string.continue_button_text) { dialog, _ ->
            dialog.dismiss()
            importCancelButton.visibility = View.VISIBLE
            importAddToDbButton.visibility = View.VISIBLE
        }
        builder.setNegativeButton(R.string.cancel_button_text) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.setView(view)
        builder.show()
    }

    private fun setupImportErrorInformation(view: View) {
        val numErrors = if (vm.errors.value != null) vm.errors.value!!.size else 0
        if (numErrors > 0) {
            view.importErrorsText.text = getString(R.string.import_errors_text).format(numErrors)
            view.importErrorsText.visibility = View.VISIBLE
            view.importErrorsList.visibility = View.VISIBLE
        } else {
            view.importErrorsText.visibility = View.GONE
            view.importErrorsList.visibility = View.GONE
        }
        setupImportErrorList(view.importErrorsList)
    }

    private fun setupImportErrorList(errorsList: RecyclerView) {
        val adapter = ErrorListAdapter()
        errorsList.adapter = adapter
        errorsList.layoutManager = LinearLayoutManager(this)

        vm.errors.observe(this, Observer { events ->
            events?.let {
                adapter.update(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMPORT_REQUEST && resultCode == RESULT_OK && data != null) {
            contentResolver.openInputStream(data.data as Uri)?.let {
                vm.import(it)
            }
        } else {
            finish()
        }
    }
}