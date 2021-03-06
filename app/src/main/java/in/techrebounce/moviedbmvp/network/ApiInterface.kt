package `in`.techrebounce.moviedbmvp.network

import `in`.techrebounce.moviedbmvp.model.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/now_playing")
    fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("page") pageNo: Int,
        @Query("region") region: String
    ): Call<MovieListResponse>
}