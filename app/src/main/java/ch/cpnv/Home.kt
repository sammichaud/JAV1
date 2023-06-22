package ch.cpnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.adapters.RVBooksAdapter

enum class Mode {
    All, Lended,
}

class Home : Fragment() {

    private lateinit var rvBooks: RecyclerView
    private var mode: Mode = Mode.All
    private lateinit var btnLend: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvBooks = view.findViewById(R.id.rvBooks)
        rvBooks.layoutManager = LinearLayoutManager(requireContext())

        btnLend = view.findViewById(R.id.btn_lend)
        btnLend.setOnClickListener { switchMode() }

        this.display()
        return view
    }

    private fun switchMode() {
        mode = when (mode) {
            Mode.All -> Mode.Lended
            Mode.Lended -> Mode.All
        }
        display()
    }

    private fun display() {
        if (mode == Mode.All) {
            val books = JAV1.db.bookDao().getAll()
            rvBooks.adapter = RVBooksAdapter(books, parentFragmentManager)
            btnLend.text = "Lended"
        } else if (mode == Mode.Lended) {
            val books = JAV1.db.bookDao().getLendsBooks()
            rvBooks.adapter = RVBooksAdapter(books, parentFragmentManager)
            btnLend.text = "All"
        }

    }
}