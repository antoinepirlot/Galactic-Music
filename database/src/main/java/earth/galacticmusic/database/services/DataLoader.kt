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

package earth.galacticmusic.database.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import earth.galacticmusic.database.R
import earth.galacticmusic.database.models.Album
import earth.galacticmusic.database.models.Artist
import earth.galacticmusic.database.models.Folder
import earth.galacticmusic.database.models.Genre
import earth.galacticmusic.database.models.Music
import earth.galacticmusic.database.services.utils.computeString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Antoine Pirlot on 22/02/24
 */

object DataLoader {
    private const val FIRST_FOLDER_INDEX: Long = 1
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
                // TODO find a way to coroutine this and fix issue with recomposition with sorted map
                val folderId: MutableLongState = mutableLongStateOf(FIRST_FOLDER_INDEX)
                while (it.moveToNext()) {
                    loadData(cursor = it, context = context, folderId = folderId)
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
    private fun loadData(cursor: Cursor, context: Context, folderId: MutableLongState) {
        var artist: Artist? = null
        var album: Album? = null
        val music: Music

        //Load album
        if (albumIdColumn != null && albumNameColumn != null) {
            album = loadAlbum(context = context, cursor = cursor)
            DataManager.albumMap[album.title] = album
        }

        //Load music
        try {
            music = loadMusic(context = context, cursor = cursor, album = album)
            DataManager.musicMediaItemSortedMap[music] = music.mediaItem
        } catch (_: IllegalAccessError) {
            // No music found
            if (album != null && album.musicSortedMap.isEmpty()) {
                DataManager.albumMap.remove(album.title)
            }
            return // Continue the while loop
        }

        //Load Folder
        loadFolders(context = context, music = music, folderId = folderId)

        //Load Genre
        try {
            var genre: Genre = loadGenre(context = context, cursor = cursor)
            DataManager.genreMap.putIfAbsent(genre.title, genre)
            // The id is not the same for all same genre
            genre = DataManager.genreMap[genre.title]!!
            music.genre = genre
            genre.addMusic(music)
        } catch (_: Exception) {
            //No Genre
        }

        //Load Artist
        if (artistIdColumn != null && artistNameColumn != null) {
            artist = loadArtist(context = context, cursor = cursor)
            DataManager.artistMap.putIfAbsent(artist.title, artist)
            //The id is not the same for all same artists
            artist = DataManager.artistMap[artist.title]!!
            artist.musicList.add(music)
            artist.musicMediaItemSortedMap.putIfAbsent(music, music.mediaItem)
            music.artist = artist
        }

        //Link album and artist if exists
        if (artist != null && album != null) {
            artist.addAlbum(album)
            album.artist = artist
        }
    }

    /**
     * Create a music object from the cursor and add it to the music list
     *
     * @param cursor the cursor where music's data is stored
     *
     * @return the created music
     */
    private fun loadMusic(context: Context, cursor: Cursor, album: Album?): Music {
        // Get values of columns for a given music.
        val relativePath: String = cursor.getString(relativePathColumn!!)
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
            relativePath = relativePath,
            displayName = displayName,
            duration = duration,
            size = size,
            album = album,
            context = context
        )
    }

    /**
     * Load folders and sub-folders (create them if not exists) where the music is present
     *
     * @param music the music to add to the folder
     */
    private fun loadFolders(
        context: Context,
        music: Music,
        folderId: MutableLongState,
    ) {
        val splitPath: MutableList<String> = mutableListOf()
        music.relativePath.split("/").forEach {
            splitPath.add(computeString(context = context, string = it))
        }

        if (splitPath.last() == context.resources.getString(R.string.unknown)) {
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
            rootFolder = Folder(
                id = folderId.longValue,
                title = splitPath[0],
                context = context
            )
            DataManager.folderMap[folderId.longValue] = rootFolder!!
            folderId.longValue++
            DataManager.rootFolderMap[rootFolder!!.id] = rootFolder!!
        }

        splitPath.removeAt(0)
        rootFolder!!.createSubFolders(
            splitPath.toMutableList(),
            folderId,
            DataManager.folderMap
        )
        val subfolder = rootFolder!!.getSubFolder(splitPath.toMutableList())!!

        subfolder.addMusic(music)
    }

    private fun loadAlbum(context: Context, cursor: Cursor): Album {
        val id: Long = cursor.getLong(albumIdColumn!!)
        val name = cursor.getString(albumNameColumn!!)

        return Album(id = id, title = name, context = context)
    }

    private fun loadArtist(context: Context, cursor: Cursor): Artist {
        // Get values of columns for a given artist.
        val id = cursor.getLong(artistIdColumn!!)
        val name = cursor.getString(artistNameColumn!!)

        return Artist(id = id, title = name, context = context)
    }

    private fun loadGenre(context: Context, cursor: Cursor): Genre {
        val id = cursor.getLong(genreIdColumn!!)
        val name = cursor.getString(genreNameColumn!!)

        return Genre(id = id, title = name, context = context)
    }
}