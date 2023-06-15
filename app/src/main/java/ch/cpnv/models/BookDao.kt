package ch.cpnv.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE id = :id")
    fun findById(id: String): Book

    @Query("SELECT * FROM books WHERE title LIKE :title LIMIT 10")
    fun findByTitle(title: String): Book

    @Insert
    fun insert(vararg books: Book)

    @Delete
    fun delete(user: Book)
}