package earth.mp3.data

data class Song(
    val id: Long,
    var name: String,
    var album: Album?,
    var artist: Artist?
)