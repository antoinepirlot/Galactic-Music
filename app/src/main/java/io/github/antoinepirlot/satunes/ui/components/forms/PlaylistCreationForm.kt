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

package io.github.antoinepirlot.satunes.ui.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 30/03/2024
 */

@Composable
internal fun PlaylistCreationForm(
    modifier: Modifier = Modifier,
    onConfirm: (playlistTitle: String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var playlistTitle: String by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        icon = {
            val icon: SatunesIcons = SatunesIcons.PLAYLIST_ADD
            Icon(
                imageVector = icon.imageVector,
                contentDescription = "Playlist Creation Button Icon"
            )
        },
        title = {
            NormalText(text = stringResource(id = R.string.create_playlist))
        },
        text = {
            Column(
                modifier = modifier
            ) {
                OutlinedTextField(
                    value = playlistTitle,
                    onValueChange = { playlistTitle = it },
                    label = { NormalText(text = stringResource(id = R.string.playlist_form)) }
                )
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirm(playlistTitle) }) {
                NormalText(text = stringResource(id = R.string.create))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                NormalText(text = stringResource(id = R.string.cancel))
            }
        }
    )

}

@Preview
@Composable
private fun PlaylistCreationFormPreview() {
    PlaylistCreationForm(onConfirm = {}, onDismissRequest = {})
}