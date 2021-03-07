package `in`.techrebounce.moviedbmvp.contract

import `in`.techrebounce.moviedbmvp.model.Movie
import android.app.Application

interface MovieListContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(movieArrayList: List<Movie>)
            fun onFailure(t: Throwable)
        }

        fun getMovieList(onFinishedListener: OnFinishedListener, pageNo: Int)
        fun insert(movieList: List<Movie>)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(movieArrayList: List<Movie>)
        fun onResponseFailure(t: Throwable)
    }

    interface Presenter {
        fun onDestroy()
        fun getMoreData(pageNo: Int)
        fun requestDataFromServer()
    }
}