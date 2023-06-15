package ch.cpnv.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.cpnv.models.Book
import ch.cpnv.models.BookDao

@Database(entities = [Book::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun bookDao(): BookDao
}