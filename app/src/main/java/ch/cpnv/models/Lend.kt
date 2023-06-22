package ch.cpnv.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lends")
data class Lend(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "bookId")
    val bookId: Int,

    @ColumnInfo(name = "lendTo")
    val lendTo: String,

    @ColumnInfo(name = "lendAt")
    val lendAt: String,

    @ColumnInfo(name = "status")
    val status: String,
)
