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

package io.github.antoinepirlot.satunes.ui.views.playlist

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.models.tables.Playlist
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.database.services.DatabaseManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openCurrentMusic
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.services.MediaSelectionManager
import io.github.antoinepirlot.satunes.ui.components.buttons.ExtraButton
import io.github.antoinepirlot.satunes.ui.components.dialog.MediaSelectionDialog
import io.github.antoinepirlot.satunes.ui.components.texts.Title
import io.github.antoinepirlot.satunes.ui.views.MediaListView
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
internal fun PlaylistView(
    modifier: Modifier = Modifier,
    playlist: PlaylistWithMusics,
) {
    //TODO try using nav controller instead try to remember it in an object if possible
    var openAddMusicsDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    val playbackController: PlaybackController = PlaybackController.getInstance()
    val musicMap: SortedMap<Music, MediaItem> = remember { playlist.musicMediaItemSortedMap }

    //Recompose if data changed
    var mapChanged: Boolean by rememberSaveable { playlist.musicMediaItemSortedMapUpdate }
    if (mapChanged) {
        mapChanged = false
    }
    //

    MediaListView(
        modifier = modifier,
        mediaList = musicMap.keys.toList(),
        openMedia = { clickedMedia: Media ->
            playbackController.loadMusic(
                musicMediaItemSortedMap = playlist.musicMediaItemSortedMap,
                musicToPlay = clickedMedia as Music
            )
            openMedia(media = clickedMedia)
        },
        openedPlaylistWithMusics = playlist,
        onFABClick = { openCurrentMusic() },
        header = { Title(text = playlist.playlist.title) },
        extraButtons = {
            ExtraButton(icon = SatunesIcons.ADD, onClick = { openAddMusicsDialog = true })
            if (playlist.musicMediaItemSortedMap.isNotEmpty()) {
                ExtraButton(icon = SatunesIcons.PLAY, onClick = {
                    playbackController.loadMusic(musicMediaItemSortedMap = playlist.musicMediaItemSortedMap)
                    openMedia()
                })
                ExtraButton(icon = SatunesIcons.SHUFFLE, onClick = {
                    playbackController.loadMusic(
                        musicMediaItemSortedMap = playlist.musicMediaItemSortedMap,
                        shuffleMode = true
                    )
                    openMedia()
                })
            }
        },
        emptyViewText = stringResource(id = R.string.no_music_in_playlist)
    )
    if (openAddMusicsDialog) {
        val allMusic: List<Music> = DataManager.musicMediaItemSortedMap.keys.toList()
        val context: Context = LocalContext.current
        MediaSelectionDialog(
            onDismissRequest = { openAddMusicsDialog = false },
            onConfirm = {
                val db = DatabaseManager(context = context)
                db.insertMusicsToPlaylist(
                    musics = MediaSelectionManager.getCheckedMusics(),
                    playlist = playlist
                )
                openAddMusicsDialog = false
            },
            mediaList = allMusic,
            icon = SatunesIcons.PLAYLIST_ADD,
            playlistTitle = playlist.playlist.title
        )
    }
}

@Preview
@Composable
private fun PlaylistViewPreview() {
    PlaylistView(
        playlist = PlaylistWithMusics(
            playlist = Playlist(id = 0, title = "Playlist"),
            musics = mutableListOf()
        )
    )
}