package ch.cpnv.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.cpnv.models.Book
import ch.cpnv.models.BookDao
import ch.cpnv.models.Lend
import ch.cpnv.models.LendDao

@Database(entities = [Book::class, Lend::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun lendDao(): LendDao
}