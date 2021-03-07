package `in`.techrebounce.moviedbmvp.database

import `in`.techrebounce.moviedbmvp.dao.MovieDao
import `in`.techrebounce.moviedbmvp.model.Movie
import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        lateinit var INSTANCE: MovieDatabase
        fun getInstance(context: Context): MovieDatabase {
            synchronized(MovieDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "nowplayingmovies.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .build()
            }
            return INSTANCE
        }

        var callback: Callback = object : Callback() {
            override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsyn(INSTANCE)
            }
        }
    }

    internal class PopulateDbAsyn(movieDatabase: MovieDatabase) : AsyncTask<Void, Void, Void>() {
        private val movieDao: MovieDao

        init {
            movieDao = movieDatabase.movieDao()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            movieDao.deleteAll()
            return null
        }
    }

}