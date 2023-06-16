package ch.cpnv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import ch.cpnv.models.Book

class AddBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = mutableListOf<String>()
        for (book in JAV1.db.bookDao().getAll()){
            items.add(book.title)
        }

        val bookList = findViewById<Spinner>(R.id.book_list)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookList.adapter = adapter


        val title = findViewById<EditText>(R.id.book_title)
        val description = findViewById<EditText>(R.id.book_description)
        val isbn = findViewById<EditText>(R.id.book_isbn)

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            addBook(title.text.toString(), description.text.toString(), isbn.text.toString())
            title.text.clear()
            description.text.clear()
            isbn.text.clear()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addBook(title: String, description: String, ISBN: String) {
        val book = Book(title = title, description = description, ISBN = ISBN)
        JAV1.db.bookDao().insert(book)
    }
}