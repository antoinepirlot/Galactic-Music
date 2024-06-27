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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.models.tables.Playlist
import io.github.antoinepirlot.satunes.services.MediaSelectionManager
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 30/03/2024
 */

@Composable
internal fun MediaSelectionCheckbox(
    modifier: Modifier = Modifier,
    media: Media
) {
    val checked: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val text: String = if (media is PlaylistWithMusics) media.playlist.title
    else media.title

    Box(modifier = modifier.clickable { onClick(checked, media) }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = checked.value, onCheckedChange = { onClick(checked, media) })
            Spacer(modifier = modifier.size(10.dp))
            NormalText(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text
            )
        }
    }
}

private fun onClick(checked: MutableState<Boolean>, media: Media) {
    checked.value = !checked.value
    if(checked.value) {
        if (media is PlaylistWithMusics) {
            MediaSelectionManager.addPlaylist(playlistWithMusics = media)
        } else if (media is Music) {
            MediaSelectionManager.addMusic(music = media)
        }
    } else {
        if (media is PlaylistWithMusics) {
            MediaSelectionManager.removePlaylist(playlistWithMusics = media)
        } else if (media is Music) {
            MediaSelectionManager.removeMusic(music = media)
        }
    }
}

@Preview
@Composable
private fun PlaylistSelectionCheckboxPreview() {
    MediaSelectionCheckbox(
        media = PlaylistWithMusics(
            playlist = Playlist(id = 0, title = ""),
            musics = mutableListOf()
        )
    )
}