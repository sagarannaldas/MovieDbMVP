package `in`.techrebounce.moviedbmvp.service

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.model.MovieListResponse
import `in`.techrebounce.moviedbmvp.network.ApiClient
import `in`.techrebounce.moviedbmvp.network.ApiInterface
import `in`.techrebounce.moviedbmvp.utils.Constants
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListModel : MovieListContract.Model {

    private var pageNo = 1

    companion object {
        private const val TAG = "MovieListModel"
    }

    override fun getMovieList(
        onFinishedListener: MovieListContract.Model.OnFinishedListener,
        pageNo: Int
    ) {
        var apiService: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        var call: Call<MovieListResponse> = apiService.getMovieList(Constants.API_KEY, pageNo , Constants.REGION)

        call.enqueue(object : Callback<MovieListResponse> {

            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                var movies: List<Movie> = response.body()?.results!!
                Log.d(TAG, "onResponse: Movie Lists Size ${movies.size}")
                onFinishedListener.onFinished(movies)
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.toString()}")
                onFinishedListener.onFailure(t)
            }

        })
    }
}