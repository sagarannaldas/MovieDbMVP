package `in`.techrebounce.moviedbmvp.model

data class MovieListResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)