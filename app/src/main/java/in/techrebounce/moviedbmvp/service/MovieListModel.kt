package `in`.techrebounce.moviedbmvp.service

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.database.MovieDatabase
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.model.MovieListResponse
import `in`.techrebounce.moviedbmvp.network.ApiClient
import `in`.techrebounce.moviedbmvp.network.ApiInterface
import `in`.techrebounce.moviedbmvp.utils.Constants
import android.app.Application
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListModel(application: Application) : MovieListContract.Model {

    lateinit var movieDatabase: MovieDatabase
    private lateinit var movieList: List<Movie>
    private val TAG = "MovieListModel"
    private var applicationContext1 = application

    override fun getMovieList(
        onFinishedListener: MovieListContract.Model.OnFinishedListener,
        pageNo: Int,
    ) {
        movieDatabase = MovieDatabase.getInstance(applicationContext1)

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
                } else {
                    onFinishedListener.onFailure(t)
                }
            }

        })
    }
}