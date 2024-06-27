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

package io.github.antoinepirlot.satunes.ui.views.genre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Album
import io.github.antoinepirlot.satunes.database.models.Genre
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openCurrentMusic
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.ui.components.buttons.ExtraButton
import io.github.antoinepirlot.satunes.ui.views.MediaListView
import io.github.antoinepirlot.satunes.ui.views.MediaWithAlbumsHeaderView
import java.util.SortedMap
import java.util.SortedSet

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
internal fun GenreView(
    modifier: Modifier = Modifier,
    genre: Genre,
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()
    val musicMap: SortedMap<Music, MediaItem> = remember { genre.musicMediaItemSortedMap }

    //Recompose if data changed
    var mapChanged: Boolean by rememberSaveable { DataManager.musicMediaItemSortedMapUpdated }
    if (mapChanged) {
        mapChanged = false
    }
    //

    MediaListView(
        modifier = modifier,
        mediaList = musicMap.keys.toList(),

        openMedia = { clickedMedia: Media ->
            playbackController.loadMusic(
                musicMediaItemSortedMap = genre.musicMediaItemSortedMap,
                musicToPlay = clickedMedia as Music
            )
            openMedia(media = clickedMedia)
        },
        onFABClick = { openCurrentMusic() },
        header = {
            //Recompose if data changed
            @Suppress("NAME_SHADOWING")
            var mapChanged: Boolean by remember { genre.musicMediaItemSortedMapUpdate }
            if (mapChanged) {
                mapChanged = false
            }
            //

            val albumSet: SortedSet<Album> = sortedSetOf()
            musicMap.forEach { (music: Music, _: MediaItem) ->
                albumSet.add(music.album)
            }
            MediaWithAlbumsHeaderView(media = genre, albumList = albumSet.toList())
        },
        extraButtons = {
            if (genre.musicMediaItemSortedMap.isNotEmpty()) {
                ExtraButton(icon = SatunesIcons.PLAY, onClick = {
                    playbackController.loadMusic(musicMediaItemSortedMap = genre.musicMediaItemSortedMap)
                    openMedia()
                })
                ExtraButton(icon = SatunesIcons.SHUFFLE, onClick = {
                    playbackController.loadMusic(
                        musicMediaItemSortedMap = genre.musicMediaItemSortedMap,
                        shuffleMode = true
                    )
                    openMedia()
                })
            }
        },
        emptyViewText = stringResource(id = R.string.no_music)
    )
}

@Preview
@Composable
private fun GenreViewPreview() {
    GenreView(genre = Genre(id = 0, "Genre"))
}

