/*
 *  This file is part of MP3 Player.
 *
 *  MP3 Player is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with MP3 Player.
 *  If not, see <https://www.gnu.org/licenses/>.

 *  ***** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.galacticmusic.ui.components.cards.media

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
import earth.galacticmusic.database.models.Media
import earth.galacticmusic.database.models.relations.PlaylistWithMusics
import earth.galacticmusic.database.models.tables.MusicDB

/**
 * @author Antoine Pirlot on 16/01/24
 */

@Composable
fun <T : Comparable<T>> MediaCardList(
    modifier: Modifier = Modifier,
    mediaMap: Map<T, Media>,
    openMedia: (media: Media) -> Unit
) {
    val lazyState = rememberLazyListState()

    if (mediaMap.isEmpty()) {
        // It fixes issue while accessing last folder in chain
        return
    }

    Column {
        LazyColumn(
            modifier = modifier,
            state = lazyState
        ) {
            items(
                items = mediaMap.values.toList(),
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
fun <T : Comparable<T>> CardListPreview() {
    MediaCardList(
        mediaMap = sortedMapOf<T, Media>(),
        openMedia = {}
    )
}