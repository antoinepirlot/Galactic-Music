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

package io.github.antoinepirlot.satunes.ui.components.cards.media

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.models.tables.MusicDB

/**
 * @author Antoine Pirlot on 16/01/24
 */

@Composable
fun MediaCardList(
    modifier: Modifier = Modifier,
    mediaList: List<Media>,
    openMedia: (media: Media) -> Unit
) {
    val lazyState = rememberLazyListState()

    if (mediaList.isEmpty()) {
        // It fixes issue while accessing last folder in chain
        return
    }

    Column {
        LazyColumn(
            modifier = modifier,
            state = lazyState
        ) {
            items(
                items = mediaList,
                key = {
                    when (it) {
                        is PlaylistWithMusics -> it.playlist.id
                        is MusicDB -> it.music.id
                        else -> it.id
                    }
                }
            ) {
                val media: Media by remember { mutableStateOf(it) }
                MediaCard(
                    modifier = modifier,
                    media = media,
                    onClick = { openMedia(media) }
                )
            }
        }
    }
}

@Composable
@Preview
fun CardListPreview() {
    MediaCardList(
        mediaList = listOf(),
        openMedia = {}
    )
}