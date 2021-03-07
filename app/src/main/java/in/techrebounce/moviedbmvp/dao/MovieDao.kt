package `in`.techrebounce.moviedbmvp.dao

import `in`.techrebounce.moviedbmvp.model.Movie
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("DELETE FROM movie")
    fun deleteAll()
}