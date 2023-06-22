package ch.cpnv.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface LendDao {
    @Query("SELECT * FROM lends")
    fun getAll(): List<Lend>

    @Query("SELECT * FROM lends WHERE bookId=:bookId AND status=\"lend\" LIMIT 1")
    fun getByBookId(bookId: Int): Lend

    @Query("UPDATE lends set status=:status WHERE id=:lendId")
    fun updateStatusById(status: String, lendId: Int)

    @Insert
    fun insert(vararg lend: Lend)

    @Delete
    fun delete(lend: Lend)
}
