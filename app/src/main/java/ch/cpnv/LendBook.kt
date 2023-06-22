package ch.cpnv

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ch.cpnv.models.Book
import ch.cpnv.models.Lend
import java.util.Calendar
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class LendBook : Fragment() {
    private lateinit var book: Book
    private var bookId: Int? = null
    private lateinit var date: EditText
    private lateinit var textContact: TextView
    private lateinit var bookTitle: TextView
    private var lend: Lend? = null
    private lateinit var btnLend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appBar = (requireActivity() as AppCompatActivity).supportActionBar
        appBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        arguments?.let {
            bookId = it.getInt("bookId")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lend, container, false)

        view.findViewById<Button>(R.id.select_contact_button)
            .setOnClickListener { pickGoogleContact() }
        btnLend = view.findViewById(R.id.btn_lend)
        date = view.findViewById(R.id.select_date)

        date.setOnClickListener { showDatePickerDialog() }
        btnLend.setOnClickListener { saveLend() }

        textContact = view.findViewById(R.id.contact_text)
        bookTitle = view.findViewById(R.id.book_title)

        getBook()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getBook() {
        book = JAV1.db.bookDao().findById(bookId)
        bookTitle.text = book.title

        getLend()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLend() {
        lend = JAV1.db.lendDao().getByBookId(book.id)

        lend?.let {
            date.setText(it.lendAt)
            textContact.text = it.lendTo
            btnLend.text = "Rendu"
        }

        if (lend == null) {
            date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            textContact.text = ""
            btnLend.text = "Prêter"
        } else {
        }
    }

    private fun pickGoogleContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        resultLauncher.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveLend() {
        if (lend == null) {
            val newLend = Lend(
                lendTo = textContact.text as String,
                lendAt = date.text.toString(),
                bookId = book.id,
                status = "lend"
            )
            JAV1.db.lendDao().insert(newLend);
            getLend()
            return
        }
        JAV1.db.lendDao().updateStatusById("rendered", lend!!.id)
        getLend()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavHostFragment.findNavController(requireParentFragment()).popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
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
                        it, null, null, null, null
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