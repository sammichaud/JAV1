package ch.cpnv.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT books.* FROM books INNER JOIN lends ON lends.bookId = books.id AND lends.status = \"lend\"")
    fun getLendsBooks(): List<Book>

    @Query("SELECT * FROM books WHERE id = :id")
    fun findById(id: Int?): Book

    @Query("SELECT * FROM books WHERE title LIKE :title LIMIT 10")
    fun findByTitle(title: String): Book

    @Insert
    fun insert(vararg books: Book)

    @Delete
    fun delete(book: Book)
}
