package `in`.techrebounce.moviedbmvp.service

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.database.MovieDatabase
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.model.MovieListResponse
import `in`.techrebounce.moviedbmvp.network.ApiClient
import `in`.techrebounce.moviedbmvp.network.ApiInterface
import `in`.techrebounce.moviedbmvp.utils.AppExecutors
import `in`.techrebounce.moviedbmvp.utils.Constants
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListModel(application: Application) : MovieListContract.Model {

    private val TAG = "MovieListModel"
    private var applicationContext1 = application
    private lateinit var mRetrieveRecipeRunnable: RetrieveRecipeRunnable

    override fun getMovieList(
        onFinishedListener: MovieListContract.Model.OnFinishedListener,
        pageNo: Int,
    ) {
        Log.d(TAG, "getMovieList: start")

        mRetrieveRecipeRunnable =
            RetrieveRecipeRunnable(onFinishedListener, pageNo, applicationContext1)
        val handler = AppExecutors.instance.networkIO().submit(mRetrieveRecipeRunnable)

    }

    class RetrieveRecipeRunnable(
        onFinishedListener: MovieListContract.Model.OnFinishedListener,
        pageNo: Int,
        application: Application
    ) : Runnable {
        private var pageNo: Int = 1
        private var applicationContext = application
        lateinit var movieDatabase: MovieDatabase
        private lateinit var movieList: List<Movie>
        private var onFinishedListener: MovieListContract.Model.OnFinishedListener

        init {
            this.pageNo = pageNo
            this.onFinishedListener = onFinishedListener
        }

        override fun run() {
            movieDatabase = MovieDatabase.getInstance(applicationContext)
            try {
                var apiService: ApiInterface =
                    ApiClient.getClient().create(ApiInterface::class.java)
                var call: Call<MovieListResponse> =
                    apiService.getMovieList(Constants.API_KEY, pageNo, Constants.REGION)

                call.enqueue(object : Callback<MovieListResponse> {

                    override fun onResponse(
                        call: Call<MovieListResponse>,
                        response: Response<MovieListResponse>
                    ) {
                        var movies: List<Movie> = response.body()?.results!!
                        Log.d(TAG, "onResponse: Movie Lists Size ${movies.size}")
                        if (movies.isNotEmpty()) {
                            movieDatabase.movieDao().insert(movies)
                        }
                        movieList = movieDatabase.movieDao().getAll()

                        if (movieList.isNotEmpty()) {
                            Log.d(TAG, "onResponse: online $movieList")
                            onFinishedListener.onFinished(movieList)
                        }

                    }

                    override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                        movieList = movieDatabase.movieDao().getAll()
                        if (movieList.isNotEmpty()) {
                            onFinishedListener.onFinished(movieList)
                            onFinishedListener.onFailure(t)
                        } else {
                            onFinishedListener.onFailure(t)
                        }
                    }

                })
            } catch (e: IOException) {
                e.printStackTrace()
                movieDatabase.movieDao().deleteAll()
            }
        }

    }
}