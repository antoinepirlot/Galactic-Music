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

package io.github.antoinepirlot.satunes.ui.components.dialog

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.models.tables.Playlist
import io.github.antoinepirlot.satunes.database.services.DatabaseManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.services.MediaSelectionManager
import io.github.antoinepirlot.satunes.ui.components.forms.MediaSelectionForm
import io.github.antoinepirlot.satunes.ui.components.forms.PlaylistCreationForm
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 30/03/2024
 */

@Composable
internal fun MediaSelectionDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    mediaList: List<Media>,
    playlistTitle: String? = null,
    icon: SatunesIcons,
) {
    if (mediaList.isEmpty()) {
        MediaSelectionDialogListEmpty(modifier = modifier, onDismissRequest)
    } else {
        MediaSelectionDialogListNotEmpty(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            onConfirm = onConfirm,
            mediaList = mediaList,
            playlistTitle = playlistTitle,
            icon = icon
        )
    }
}

@Composable
private fun MediaSelectionDialogListEmpty(
    modifier: Modifier,
    onDismissRequest: () -> Unit
) {
    val context: Context = LocalContext.current

    PlaylistCreationForm(
        modifier = modifier,
        onConfirm = { playlistTitle: String ->
            val playlist = Playlist(id = 0, title = playlistTitle)
            DatabaseManager(context = context).insertOne(
                context = context,
                playlist = playlist
            )
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
private fun MediaSelectionDialogListNotEmpty(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    mediaList: List<Media>,
    playlistTitle: String? = null,
    icon: SatunesIcons,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(imageVector = icon.imageVector, contentDescription = icon.description)
        },
        title = {
            if (mediaList.isEmpty()) {
                NormalText(text = stringResource(id = R.string.no_music))
            } else if (mediaList[0] is Music) {
                if (playlistTitle == null) {
                    throw IllegalStateException("Playlist title is required when adding music to playlist")
                }
                NormalText(text = stringResource(id = R.string.add_to) + playlistTitle)
            } else {
                NormalText(text = stringResource(id = R.string.add_to_playlist))
            }
        },
        text = {
            Column {
                MediaSelectionForm(mediaList = mediaList)
            }
        },
        onDismissRequest = {
            MediaSelectionManager.clearCheckedMusics()
            MediaSelectionManager.clearCheckedPlaylistWithMusics()
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                MediaSelectionManager.clearCheckedMusics()
                MediaSelectionManager.clearCheckedPlaylistWithMusics()
            }) {
                if (mediaList.isNotEmpty()) {
                    NormalText(text = stringResource(id = R.string.add))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                NormalText(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Preview
@Composable
private fun PlaylistSelectionDialogPreview() {
    MediaSelectionDialog(
        icon = SatunesIcons.PLAYLIST_ADD,
        onDismissRequest = {},
        onConfirm = {},
        mediaList = listOf()
    )
}