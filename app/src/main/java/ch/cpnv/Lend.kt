package ch.cpnv

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.util.Calendar


class Lend : Fragment() {
    private lateinit var date: EditText
    private lateinit var textContact: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lend, container, false)

        view.findViewById<Button>(R.id.select_contact_button)
            .setOnClickListener { pickGoogleContact() }

        view.findViewById<Button>(R.id.btn_lend).setOnClickListener { saveLend() }


        date = view.findViewById(R.id.select_date)
        date.setOnClickListener { showDatePickerDialog() }

        textContact = view.findViewById(R.id.contact_text)

        return view
    }

    private fun pickGoogleContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun saveLend() {

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Handle the selected date
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                date.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    @SuppressLint("Range")
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val contactUri = data?.data
                val cursor = contactUri?.let {
                    requireActivity().contentResolver.query(
                        it,
                        null,
                        null,
                        null,
                        null
                    )
                }

                cursor?.use {
                    if (it.moveToFirst()) {
                        textContact.text =
                            it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    }
                }
            }
        }
}