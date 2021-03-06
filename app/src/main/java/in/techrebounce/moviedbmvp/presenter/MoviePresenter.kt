package `in`.techrebounce.moviedbmvp.presenter

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.model.Movie

class MoviePresenter(
    movieListModel: MovieListContract.Model,
    movielistView: MovieListContract.View
) : MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    var movieListModel: MovieListContract.Model
    var movielistView: MovieListContract.View

    init {
        this.movieListModel = movieListModel
        this.movielistView = movielistView
    }

    override fun onDestroy() {
    }

    override fun getMoreData(pageNo: Int) {
        if (movielistView != null) {
            movielistView.showProgress()
        }
        movieListModel.getMovieList(this, pageNo)

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