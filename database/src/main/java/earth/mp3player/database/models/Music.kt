/*
 *  This file is part of MP3 Player.
 *
 *  MP3 Player is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with MP3 Player.
 *  If not, see <https://www.gnu.org/licenses/>.
 *
 *  ***** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 *  PS: I don't answer quickly.
 */

package earth.mp3player.database.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.getSystemService
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import earth.mp3player.database.models.tables.MusicDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/**
 * @author Antoine Pirlot on 27/03/2024
 */

data class Music private constructor(
    override val id: Long,
    override var title: String,
    var displayName: String,
    val duration: Long = 0,
    val size: Int = 0,
    var relativePath: String,
    var folder: Folder? = null,
    var artist: Artist? = null,
    var album: Album? = null,
    var genre: Genre? = null,
) : Media {
    lateinit var mediaItem: MediaItem
    private var absolutePath: String = "$ROOT_PATH/$relativePath/$displayName"
    var uri: Uri = Uri.Builder().appendPath(this.absolutePath).build()
    var artwork: ImageBitmap? = null
    var musicDB: MusicDB? = null

    companion object {
        val ROOT_PATH: String = Environment.getExternalStorageDirectory().path
    }

    constructor(
        id: Long,
        title: String,
        displayName: String,
        duration: Long,
        size: Int,
        relativePath: String,
        folder: Folder? = null,
        artist: Artist? = null,
        album: Album? = null,
        genre: Genre? = null,
        context: Context
    ) : this(id, title, displayName, duration, size, relativePath, folder, artist, album, genre) {
        val storageManager = context.getSystemService<StorageManager>()
        val storageVolumes: List<StorageVolume> = storageManager!!.storageVolumes

        for (volume in storageVolumes) {
            absolutePath = "${volume.directory!!.path}/$relativePath/$displayName"
            if (!File(this.absolutePath).exists()) {
                if (storageVolumes.last() == volume) {
                    throw IllegalAccessException("This media doesn't exist")
                }
                continue
            }
            this.relativePath = "${volume.directory!!.path.split("/").last()}/$relativePath"
            this.uri = Uri.parse(absolutePath)
            break
        }

        val mediaMetaData: MediaMetadata = MediaMetadata.Builder()
            .setArtist(if (this.artist != null) this.artist!!.title else null)
            .setTitle(this.title)
            .setGenre(if (this.genre != null) this.genre!!.title else null)
            .setAlbumTitle(if (this.album != null) this.album!!.title else null)
            .build()
        this.mediaItem = MediaItem.Builder()
            .setUri(this.uri)
            .setMediaMetadata(mediaMetaData)
            .build()

        if (this.album != null) {
            this.album!!.addMusic(music = this)
        }
        loadAlbumArtwork(context = context)
    }

    /**
     * Load the artwork from a media meta data retriever.
     * Decode the byte array to set music's artwork as ImageBitmap
     * If there's an artwork add it to music as ImageBitmap.
     *
     * @param context the context
     */
    private fun loadAlbumArtwork(context: Context) {
        val music: Music = this
        //Put it in Dispatchers.IO make the app not freezing while starting
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mediaMetadataRetriever = MediaMetadataRetriever()

                mediaMetadataRetriever.setDataSource(context, music.uri)

                val artwork: ByteArray? = mediaMetadataRetriever.embeddedPicture

                if (artwork != null) {
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(artwork, 0, artwork.size)
                    music.artwork = bitmap.asImageBitmap()
                }
            } catch (_: Exception) {
                /* No artwork found*/
                music.artwork = null
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Music

        if (displayName != other.displayName) return false
        if (artist != other.artist) return false
        if (album != other.album) return false

        return true
    }

    override fun hashCode(): Int {
        var result = displayName.hashCode()
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (album?.hashCode() ?: 0)
        return result
    }
}