package `in`.techrebounce.moviedbmvp.service

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.dao.MovieDao
import `in`.techrebounce.moviedbmvp.database.MovieDatabase
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.model.MovieListResponse
import `in`.techrebounce.moviedbmvp.network.ApiClient
import `in`.techrebounce.moviedbmvp.network.ApiInterface
import `in`.techrebounce.moviedbmvp.utils.Constants
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListModel(application: Application) : MovieListContract.Model {

    private var pageNo = 1
    lateinit var movieDatabase: MovieDatabase
    private lateinit var movieList: List<Movie>
    private val TAG = "MovieListModel"
    private var applicationContext1 = application


    override fun getMovieList(
        onFinishedListener: MovieListContract.Model.OnFinishedListener,
        pageNo: Int,
    ) {

        networkRequest()

        movieDatabase = MovieDatabase.getInstance(applicationContext1)
        movieList = movieDatabase.movieDao().getAll()
        onFinishedListener.onFinished(movieList)


    }

    private fun networkRequest() {
        var apiService: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        var call: Call<MovieListResponse> =
            apiService.getMovieList(Constants.API_KEY, pageNo, Constants.REGION)

        call.enqueue(object : Callback<MovieListResponse> {

            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                var movies: List<Movie> = response.body()?.results!!
                Log.d(TAG, "onResponse: Movie Lists Size ${movies.size}")
                insert(movies)
//                onFinishedListener.onFinished(movies)
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
//                onFinishedListener.onFailure(t)
            }

        })
    }

    override fun insert(movieList: List<Movie>) {
        var insertAsyncTask = InsertAsyncTask(movieDatabase).execute(movieList)

    }

    companion object {

        class InsertAsyncTask(movieDatabase: MovieDatabase) : AsyncTask<List<Movie>, Void, Void>() {
            private val movieDao: MovieDao

            init {
                this.movieDao = movieDatabase.movieDao()
            }

            override fun doInBackground(vararg lists: List<Movie>): Void? {
                movieDao.insert(lists[0])
                return null
            }
        }
    }
}