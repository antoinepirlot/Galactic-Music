/*
 * This file is part of Satunes.
 *
 * Satunes is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Satunes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Satunes.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * **** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/Satunes
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package io.github.antoinepirlot.satunes.database.models.relations

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.Relation
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.models.tables.MusicDB
import io.github.antoinepirlot.satunes.database.models.tables.MusicsPlaylistsRel
import io.github.antoinepirlot.satunes.database.models.tables.Playlist
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 27/03/2024
 */

@Serializable
data class PlaylistWithMusics(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlist_id",
        entityColumn = "music_id",
        associateBy = Junction(MusicsPlaylistsRel::class)
    )
    val musics: MutableList<MusicDB>
) : Media {

    @Ignore
    @Transient
    override var artwork: Bitmap? = null

    @Ignore
    @Transient
    override var id: Long = playlist.id // Not used

    @Ignore
    @Transient
    override val title: String = "Title is not used for PlaylistWithMusics class." // Not used

    @Ignore
    @Transient
    override val musicMediaItemSortedMap: SortedMap<Music, MediaItem> = sortedMapOf()

    @Ignore
    @Transient
    val musicMediaItemSortedMapUpdate: MutableState<Boolean> = mutableStateOf(false)


    init {
        musics.forEach { musicDB: MusicDB ->
            if (musicDB.music != null) {
                musicMediaItemSortedMap[musicDB.music] = musicDB.music!!.mediaItem
            }
        }
    }

    fun addMusic(music: Music) {
        val musicDb = MusicDB(id = music.id)
        if (musicDb.music != null) {
            musics.add(musicDb)
            if (!musicMediaItemSortedMap.contains(music)) {
                musicMediaItemSortedMap[music] = music.mediaItem
                musicMediaItemSortedMapUpdate.value = true
            }
        }
    }

    fun removeMusic(music: Music) {
        musics.remove(MusicDB(id = music.id))
        if (musicMediaItemSortedMap.contains(music)) {
            musicMediaItemSortedMap.remove(music)
            musicMediaItemSortedMapUpdate.value = true
        }
    }
}
