package `in`.techrebounce.moviedbmvp.contract

import `in`.techrebounce.moviedbmvp.model.Movie

interface MovieListContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(movieArrayList: List<Movie>)
            fun onFailure(t: Throwable)
        }

        fun getMovieList(onFinishedListener: OnFinishedListener, pageNo: Int)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(movieArrayList: List<Movie>)
        fun onResponseFailure(t: Throwable)
    }

    interface Presenter {
        fun requestDataFromServer()
    }
}