package `in`.techrebounce.moviedbmvp.presenter

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.service.MovieListModel
import android.app.Application

class MovieListPresenter(
    movielistView: MovieListContract.View,
    applicationContext: Application
) : MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private var movieListModel: MovieListContract.Model
    private var movielistView: MovieListContract.View
    private var applicationContext1 = applicationContext

    init {
        this.movieListModel = MovieListModel(applicationContext1)
        this.movielistView = movielistView
    }

    override fun requestDataFromServer() {
        if (movielistView != null) {
            movielistView.showProgress()
        }
        movieListModel.getMovieList(this, 1)
    }

    override fun onFinished(movieArrayList: List<Movie>) {
        movielistView.setDataToRecyclerView(movieArrayList)
        if (movielistView != null) {
            movielistView.hideProgress()
        }
    }

    override fun onFailure(t: Throwable) {
        movielistView.onResponseFailure(t)
        if (movielistView != null) {
            movielistView.hideProgress()
        }
    }
}