package `in`.techrebounce.moviedbmvp

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.presenter.MovieListPresenter
import `in`.techrebounce.moviedbmvp.view.MovieListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MovieListContract.View {

    private lateinit var movieListPresenter: MovieListPresenter
    private lateinit var recyclerView: RecyclerView
    private var movieArrayList = ArrayList<Movie>()
    private lateinit var progressBar: ProgressBar
    private var pageNo = 1
    private val movieListAdapter: MovieListAdapter = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        movieListPresenter = MovieListPresenter(this)
        movieListPresenter.requestDataFromServer()
        setupRecyclerView()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

    }

    private fun setupRecyclerView() {
        val MovieListAdapter = MovieListAdapter(movieArrayList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MovieListAdapter
    }

    override fun showProgress() {

    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun setDataToRecyclerView(movieArrayList: List<Movie>) {

    }

    override fun onResponseFailure(t: Throwable) {
        TODO("Not yet implemented")
    }
}