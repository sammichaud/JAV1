package ch.cpnv.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.JAV1
import ch.cpnv.R
import ch.cpnv.models.Book

class RVBooksAdapter(private val books: List<Book>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RVBooksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVBooksAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVBooksAdapter.ViewHolder, position: Int) {
        val book = books[position]
        val lend = JAV1.db.lendDao().getByBookId(book.id)

        holder.bookTitle.text = book.title
        holder.bookISBN.text = book.ISBN
        holder.bookDescription.text = book.description
        if (lend != null) {
            holder.bookLandedAt.text = lend.lendAt
            holder.bookLandedTo.text = lend.lendTo
        }
//        holder.itemView.setOnClickListener { itemHandleClick(book) };
    }

    override fun getItemCount(): Int {
        return books.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.book_title)
        val bookISBN: TextView = itemView.findViewById(R.id.book_isbn)
        val bookDescription: TextView = itemView.findViewById(R.id.book_description)
        val bookLandedTo: TextView = itemView.findViewById(R.id.book_landed_to)
        val bookLandedAt: TextView = itemView.findViewById(R.id.book_landed_at)
    }

}