package ch.cpnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.adapters.RVBooksAdapter
import ch.cpnv.databinding.FragmentHomeBinding

enum class Mode {
    All, Lended,
}

class Home : Fragment() {

    private lateinit var _rvBooks: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        _rvBooks = view.findViewById(R.id.rvBooks)
        _rvBooks.layoutManager = LinearLayoutManager(requireContext())

        this.display()
        return view
    }

    private fun display(mode: Mode = Mode.All) {
        if (mode == Mode.All) {
            val books = JAV1.db.bookDao().getAll()
            _rvBooks.adapter = RVBooksAdapter(books, parentFragmentManager)
        } else if (mode == Mode.Lended) {
            val books = JAV1.db.bookDao().getLendsBooks()
            _rvBooks.adapter = RVBooksAdapter(books, parentFragmentManager)
        }

    }
}