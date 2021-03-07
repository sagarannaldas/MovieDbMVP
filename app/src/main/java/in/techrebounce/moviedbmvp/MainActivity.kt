package `in`.techrebounce.moviedbmvp

import `in`.techrebounce.moviedbmvp.contract.MovieListContract
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.presenter.MovieListPresenter
import `in`.techrebounce.moviedbmvp.view.MovieListAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MovieListContract.View {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var movieListPresenter: MovieListPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieArrayList: List<Movie>
    private lateinit var progressBar: ProgressBar
    private var pageNo = 1
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        movieListPresenter = MovieListPresenter(this)
        movieListPresenter.requestDataFromServer()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupRecyclerView() {
        var MovieListAdapter = MovieListAdapter(movieArrayList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MovieListAdapter
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setDataToRecyclerView(movieArrayList: List<Movie>) {
        this.movieArrayList = movieArrayList
        setupRecyclerView()
    }

    override fun onResponseFailure(t: Throwable) {
        Log.d(TAG, "onResponseFailure: ${t.message}")
        Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show()
    }
}