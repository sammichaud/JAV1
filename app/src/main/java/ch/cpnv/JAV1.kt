package ch.cpnv

import androidx.room.Room
import android.app.Application
import ch.cpnv.database.DB

class JAV1 : Application() {
    companion object {
        lateinit var db: DB
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DB::class.java,
            "BookMyBook"
        ).allowMainThreadQueries().build()
    }
}
