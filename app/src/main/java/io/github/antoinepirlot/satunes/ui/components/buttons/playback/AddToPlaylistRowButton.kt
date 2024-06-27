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

package io.github.antoinepirlot.satunes.ui.components.buttons.playback

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
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.database.services.DatabaseManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.services.MediaSelectionManager
import io.github.antoinepirlot.satunes.ui.components.dialog.MediaSelectionDialog
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 01/06/2024
 */

@Composable
internal fun AddToPlaylistRowButton(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    var showForm: Boolean by rememberSaveable { mutableStateOf(false) }

    RowButton(
        modifier = modifier,
        icon = SatunesIcons.PLAYLIST_ADD,
        text = stringResource(id = R.string.add_to_playlist),
        onClick = { showForm = true }
    )

    if (showForm) {
        val playlistMap: SortedMap<String, PlaylistWithMusics> =
            remember { DataManager.playlistWithMusicsMap }

        //Recompose if data changed
        var mapChanged: Boolean by rememberSaveable { DataManager.playlistWithMusicsMapUpdated }
        if (mapChanged) {
            mapChanged = false
        }
        //

        MediaSelectionDialog(
            onDismissRequest = { showForm = false },
            onConfirm = {
                addMusicPlayingToPlaylist(
                    context = context,
                    checkedPlaylists = MediaSelectionManager.getCheckedPlaylistWithMusics()
                )
                showForm = false
            },
            mediaList = DataManager.playlistWithMusicsMap.values.toList(),
            icon = SatunesIcons.PLAYLIST_ADD
        )
    }
}

private fun addMusicPlayingToPlaylist(
    context: Context,
    checkedPlaylists: List<PlaylistWithMusics>
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()
    val musicPlaying: Music = playbackController.musicPlaying.value!!
    val db = DatabaseManager(context = context)
    db.insertMusicToPlaylists(music = musicPlaying, playlists = checkedPlaylists)
}

@Preview
@Composable
private fun AddToPlaylistRowButtonPreview() {
    AddToPlaylistRowButton()
}