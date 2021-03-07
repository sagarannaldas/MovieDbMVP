package `in`.techrebounce.moviedbmvp.view

import `in`.techrebounce.moviedbmvp.R
import `in`.techrebounce.moviedbmvp.model.Movie
import `in`.techrebounce.moviedbmvp.utils.Constants
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter(movieList: List<Movie>, context: Context) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    private var movieList: List<Movie>
    private var context: Context

    init {
        this.movieList = movieList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_movie_list,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]
        holder.textViewTitle.setText(movie.original_title)
        holder.textViewDate.setText(
            "${context.getString(R.string.date)}  ${movie.release_date}"
        )
        holder.textViewLanguage.setText(
            "${context.getString(R.string.language)}  ${movie.original_language}"
        )
        holder.textViewPopularity.setText(
            "${context.getString(R.string.popularity)}  ${movie.popularity}"
        )
        holder.textViewDescription.setText("${context.getString(R.string.description)} ${movie.overview}")

        val movieImageUrl = Constants.IMAGE_BASE_URL + movie.poster_path

        Glide.with(holder.itemView).load(movieImageUrl).into(holder.imageViewposter)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewMovieTitle)
        val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguage)
        val textViewPopularity: TextView = itemView.findViewById(R.id.textViewPopularity)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val imageViewposter: ImageView = itemView.findViewById(R.id.imageView)
    }
}