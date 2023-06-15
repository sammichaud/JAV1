package ch.cpnv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ch.cpnv.models.Book

class AddBook : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_book, container, false)

        val title = view.findViewById<EditText>(R.id.book_title)
        val description = view.findViewById<EditText>(R.id.book_description)
        val isbn = view.findViewById<EditText>(R.id.book_isbn)

        view.findViewById<Button>(R.id.btn_add).setOnClickListener { addBook(title.text.toString(), description.text.toString(), isbn.text.toString()) }

        title.text.clear()
        description.text.clear()
        isbn.text.clear()

        return view
    }

    private fun addBook(title: String, description: String, ISBN: String) {
        val book = Book(title = title, description = description, ISBN = ISBN)
        JAV1.db.bookDao().insert(book)
        Toast.makeText(requireContext(), "Le livre a bien été ajouté", Toast.LENGTH_SHORT).show()
    }
}