/*
 * This file is part of Satunes.
 *
 *  Satunes is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  Satunes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Satunes.
 *  If not, see <https://www.gnu.org/licenses/>.
 *
 *  **** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/Satunes
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 *  PS: I don't answer quickly.
 */

package earth.satunes.database.services

import androidx.media3.common.MediaItem
import earth.satunes.database.exceptions.DuplicatedAlbumException
import earth.satunes.database.exceptions.MusicNotFoundException
import earth.satunes.database.models.Album
import earth.satunes.database.models.Artist
import earth.satunes.database.models.Folder
import earth.satunes.database.models.Genre
import earth.satunes.database.models.Music
import earth.satunes.database.models.relations.PlaylistWithMusics
import earth.satunes.database.models.tables.Playlist
import java.util.SortedMap
import java.util.SortedSet

/**
 * @author Antoine Pirlot on 07/03/2024
 */

object DataManager {
    val musicMediaItemSortedMap: SortedMap<Music, MediaItem> = sortedMapOf()
    private val musicMapById: MutableMap<Long, Music> = mutableMapOf()
    val rootFolderMap: SortedMap<Long, Folder> = sortedMapOf()
    val folderMap: SortedMap<Long, Folder> = sortedMapOf()
    val artistMap: SortedMap<String, Artist> = sortedMapOf()
    private val artistMapById: MutableMap<Long, Artist> = mutableMapOf()
    val albumSet: SortedSet<Album> = sortedSetOf()
    private val albumMapById: MutableMap<Long, Album> = mutableMapOf()
    val genreMap: SortedMap<String, Genre> = sortedMapOf()
    private val genreMapById: MutableMap<Long, Genre> = mutableMapOf()
    val playlistWithMusicsMap: SortedMap<String, PlaylistWithMusics> = sortedMapOf() //TODO Remove
    private val playlistWithMusicsMapById: MutableMap<Long, PlaylistWithMusics> = mutableMapOf()

    fun getMusic(musicId: Long): Music {
        try {
        return musicMapById[musicId]!!
        } catch (_: NullPointerException) {
            //That means the music is not more present in the phone storage
            //Happens when the database is loaded with old informations.
            throw MusicNotFoundException(musicId = musicId)
        }
    }

    fun getMusic(mediaItem: MediaItem): Music {
        return getMusic(musicId = mediaItem.mediaId.toLong())
    }

    fun getMediaItem(music: Music): MediaItem {
        return musicMediaItemSortedMap[music]!!
    }

    fun addMusic(music: Music) {
        musicMediaItemSortedMap.putIfAbsent(music, music.mediaItem)
        musicMapById.putIfAbsent(music.id, music)
    }

    fun getArtist(artist: Artist): Artist {
        return artistMapById[artist.id]!!
    }

    fun getArtist(artistId: Long): Artist {
        return artistMapById[artistId]!!
    }

    fun getArtist(artistName: String): Artist {
        return artistMap[artistName]!!
    }

    fun addArtist(artist: Artist): Artist {
        artistMap.putIfAbsent(artist.title, artist)
        //You can have multiple same artist's name but different id, but it's the same artist.
        val artistToReturn: Artist = artistMap[artist.title]!!
        artistMapById.putIfAbsent(artistToReturn.id, artist)
        return artistToReturn
    }

    fun removeArtist(artist: Artist) {
        artistMap.remove(artist.title)
        artistMapById.remove(artist.id)
    }

    fun getAlbum(albumId: Long): Album {
        return albumMapById[albumId]!!
    }

    fun getAlbum(albumName: String): Album {
        return albumSet.first { it.title == albumName }
    }

    fun addAlbum(album: Album) {
        if (albumMapById.containsValue(value = album)) {
            val existingAlbum: Album = albumMapById.values.first { it == album }
            throw DuplicatedAlbumException(existingAlbum = existingAlbum)
        }
        albumSet.add(album)
        albumMapById.putIfAbsent(album.id, album)
    }

    fun removeAlbum(album: Album) {
        albumSet.remove(album)
        albumMapById.remove(album.id)
    }

    fun getFolder(folderId: Long): Folder {
        return folderMap[folderId]!!
    }

    fun addFolder(folder: Folder) {
        folderMap.putIfAbsent(folder.id, folder)
        if (folder.parentFolder == null) {
            rootFolderMap.putIfAbsent(folder.id, folder)
        }
    }

    fun removeFolder(folder: Folder) {
        folderMap.remove(folder.id)
        rootFolderMap.remove(folder.id)
    }

    fun getGenre(genreId: Long): Genre {
        return genreMapById[genreId]!!
    }

    fun getGenre(genreName: String): Genre {
        return genreMap[genreName]!!
    }

    fun addGenre(genre: Genre): Genre {
        genreMap.putIfAbsent(genre.title, genre)
        //You can have multiple same genre's name but different id, but it's the same genre.
        val genreToReturn: Genre = genreMap[genre.title]!!
        genreMapById.putIfAbsent(genreToReturn.id, genre)
        return genreToReturn
    }

    fun removeGenre(genre: Genre) {
        genreMap.remove(genre.title)
        genreMapById.remove(genre.id)
    }

    fun getPlaylist(playlistId: Long): PlaylistWithMusics {
        return playlistWithMusicsMapById[playlistId]!!
    }

    fun addPlaylist(playlistWithMusics: PlaylistWithMusics) {
        val playlist: Playlist = playlistWithMusics.playlist
        playlistWithMusicsMap.putIfAbsent(playlist.title, playlistWithMusics)
        playlistWithMusicsMapById.putIfAbsent(playlist.id, playlistWithMusics)
    }

    fun removePlaylist(playlistWithMusics: PlaylistWithMusics) {
        playlistWithMusicsMap.remove(playlistWithMusics.playlist.title)
        playlistWithMusicsMapById.remove(playlistWithMusics.playlist.id)
    }
}