/*
 * This file is part of MP3 Player.
 *
 * MP3 Player is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MP3 Player.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * **** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.mp3player.database.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.getSystemService
import earth.mp3player.database.R
import earth.mp3player.database.models.Album
import earth.mp3player.database.models.Artist
import earth.mp3player.database.models.Folder
import earth.mp3player.database.models.Genre
import earth.mp3player.database.models.Music
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/**
 * @author Antoine Pirlot on 22/02/24
 */

object DataLoader {
    private val URI: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    var isLoaded: Boolean = false
    var isLoading: MutableState<Boolean> = mutableStateOf(false)

    // Music variables
    private var musicIdColumn: Int? = null
    private var musicNameColumn: Int? = null
    private var musicTitleColumn: Int? = null
    private var musicDurationColumn: Int? = null
    private var musicSizeColumn: Int? = null
    private var relativePathColumn: Int? = null

    // Albums variables
    private var albumIdColumn: Int? = null
    private var albumNameColumn: Int? = null

    // Artists variables
    private var artistIdColumn: Int? = null
    private var artistNameColumn: Int? = null

    //Genres variables
    private var genreIdColumn: Int? = null
    private var genreNameColumn: Int? = null

    /**
     * Load all Media data from device's storage.
     */
    fun loadAllData(context: Context) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val projection = arrayOf(
                // AUDIO
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.RELATIVE_PATH,

                //ALBUMS
                MediaStore.Audio.Albums.ALBUM_ID,
                MediaStore.Audio.Albums.ALBUM,

                //ARTISTS
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,

                //Genre
                MediaStore.Audio.Media.GENRE_ID,
                MediaStore.Audio.Media.GENRE
            )

            context.contentResolver.query(URI, projection, null, null)?.use {
                loadColumns(cursor = it)
                while (it.moveToNext()) {
                    loadData(cursor = it, context = context)
                }
            }

            DatabaseManager(context = context).loadAllPlaylistsWithMusic()
            isLoaded = true
            isLoading.value = false
        }
    }

    /**
     * Cache columns and columns indices for data to load
     */
    private fun
            loadColumns(cursor: Cursor) {
        // Cache music columns indices.
        musicIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        musicNameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
        musicTitleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        musicDurationColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        musicSizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
        relativePathColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH)

        //Cache album columns indices
        try {
            albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            albumNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
        } catch (_: IllegalArgumentException) {
            // No album
        }

        // Cache artist columns indices.
        try {
            artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
        } catch (_: IllegalArgumentException) {
            // No artist
        }

        // Cache Genre columns indices.
        try {
            genreIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE_ID)
            genreNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE)
        } catch (_: IllegalArgumentException) {
            // No genre
        }
    }

    /**
     * Load data from cursor
     */
    private fun loadData(cursor: Cursor, context: Context) {
        var artist: Artist? = null
        var album: Album? = null
        var genre: Genre? = null

        //Load album
        try {
            album = loadAlbum(cursor = cursor)
        } catch (_: Exception) {
            //No Album
        }

        //Load Artist
        try {
            artist = loadArtist(cursor = cursor)
        } catch (_: Exception) {
            //No artist
        }

        //Link album and artist if exists
        if (artist != null && album != null) {
            artist.addAlbum(album)
            album.artist = artist
        }

        //Load Genre
        try {
            genre = loadGenre(cursor = cursor)
        } catch (_: Exception) {
            //No genre
        }

        //Load Folder
        val displayName: String = cursor.getString(musicNameColumn!!)
        val relativePath: String = cursor.getString(relativePathColumn!!) + displayName
        val absolutePath: String =
            Uri.encode(getAbsolutePath(context = context, relativePath = relativePath))

        val folder: Folder = loadFolder(context = context, absolutePath = absolutePath)

        //Load music and folder inside load music function
        try {
            loadMusic(
                context = context,
                cursor = cursor,
                album = album,
                artist = artist,
                folder = folder,
                genre = genre,
                absolutePath = absolutePath,
            )
        } catch (_: IllegalAccessError) {
            // No music found
            if (album != null && album.musicSortedMap.isEmpty()) {
                DataManager.albumMap.remove(album.title)
            }
        }
    }

    /**
     * Create a music object from the cursor and add it to the music list
     *
     * @param cursor the cursor where music's data is stored
     *
     * @return the created music
     */
    private fun loadMusic(
        context: Context,
        cursor: Cursor,
        album: Album?,
        artist: Artist?,
        folder: Folder,
        genre: Genre?,
        absolutePath: String,
    ): Music {
        // Get values of columns for a given music.
        val id: Long = cursor.getLong(musicIdColumn!!)
        if (id < 1) {
            throw IllegalArgumentException("The id is less than 1")
        }
        val size = cursor.getInt(musicSizeColumn!!)
        if (size < 0) {
            throw IllegalArgumentException("Size is less than 0")
        }
        val duration: Long = cursor.getLong(musicDurationColumn!!)
        if (duration < 0) {
            throw IllegalArgumentException("Duration is less than 0")
        }
        val displayName: String = cursor.getString(musicNameColumn!!)
        var title: String = cursor.getString(musicTitleColumn!!)
        if (title.isBlank()) {
            title = displayName
        }

        return Music(
            id = id,
            title = title,
            absolutePath = absolutePath,
            displayName = displayName,
            duration = duration,
            size = size,
            album = album,
            artist = artist,
            folder = folder,
            genre = genre,
            context = context
        )
    }

    private fun getAbsolutePath(context: Context, relativePath: String): String {
        var absolutePath = ""
        val storageManager = context.getSystemService<StorageManager>()
        val storageVolumes: List<StorageVolume> = storageManager!!.storageVolumes
        for (volume in storageVolumes) {
            absolutePath = "${volume.directory!!.path}/${relativePath}"
            if (!File(absolutePath).exists()) {
                if (storageVolumes.last() == volume) {
                    throw IllegalAccessException("This media doesn't exist")
                }
                continue
            }
        }
        return absolutePath
    }

    /**
     * Load folder (create it if not exists) where the music is present
     *
     * @param cursor the cursor containing music informations
     * @param context the context :p
     * @param nextFolderId the next folder id
     */
    private fun loadFolder(
        context: Context,
        absolutePath: String,
    ): Folder {
        val splitPath: MutableList<String> = mutableListOf()
        Uri.decode(absolutePath).split("/").forEach {
            if (it !in listOf("", "storage", "emulated")) {
                splitPath.add(Uri.encode(it))
            }
        }


        val last: String = splitPath.last()
        if (last.isBlank() || last == context.resources.getString(R.string.unknown)) {
            //remove the blank folder
            splitPath.removeLast()
        }

        var rootFolder: Folder? = null

        DataManager.rootFolderMap.values.forEach { folder: Folder ->
            if (folder.title == splitPath[0]) {
                rootFolder = folder
                return@forEach
            }
        }

        if (rootFolder == null) {
            // No root folders in the list
            rootFolder = Folder(title = splitPath[0])
            DataManager.folderMap[rootFolder!!.id] = rootFolder!!
            DataManager.rootFolderMap[rootFolder!!.id] = rootFolder!!
        }

        splitPath.removeAt(0)
        rootFolder!!.createSubFolders(splitPath.toMutableList())
        return rootFolder!!.getSubFolder(splitPath.toMutableList())!!
    }

    private fun loadAlbum(cursor: Cursor): Album {
        val id: Long = cursor.getLong(albumIdColumn!!)
        val name = Uri.encode(cursor.getString(albumNameColumn!!))

        val album = Album(id = id, title = name)
        DataManager.albumMap[album.title] = album
        return album
    }

    private fun loadArtist(cursor: Cursor): Artist {
        // Get values of columns for a given artist.
        val id = cursor.getLong(artistIdColumn!!)
        val name = Uri.encode(cursor.getString(artistNameColumn!!))

        val artist = Artist(id = id, title = name)
        DataManager.artistMap.putIfAbsent(artist.title, artist)
        //The id is not the same for all same artists
        return DataManager.artistMap[artist.title]!!
    }

    private fun loadGenre(cursor: Cursor): Genre {
        val id = cursor.getLong(genreIdColumn!!)
        val name = Uri.encode(cursor.getString(genreNameColumn!!))

        val genre = Genre(id = id, title = name)
        DataManager.genreMap.putIfAbsent(genre.title, genre)
        // The id is not the same for all same genre
        return DataManager.genreMap[genre.title]!!
    }
}