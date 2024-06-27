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

package io.github.antoinepirlot.satunes.ui.views.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Album
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openCurrentMusic
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.ui.ScreenSizes
import io.github.antoinepirlot.satunes.ui.components.buttons.ExtraButton
import io.github.antoinepirlot.satunes.ui.components.images.AlbumArtwork
import io.github.antoinepirlot.satunes.ui.components.texts.Subtitle
import io.github.antoinepirlot.satunes.ui.components.texts.Title
import io.github.antoinepirlot.satunes.ui.views.MediaListView
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
internal fun AlbumView(
    modifier: Modifier = Modifier,
    album: Album,
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()


    val musicMap: SortedMap<Music, MediaItem> = remember { album.musicMediaItemSortedMap }

    //Recompose if data changed
    var mapChanged: Boolean by rememberSaveable { album.musicMediaItemSortedMapUpdate }
    if (mapChanged) {
        mapChanged = false
    }
    //

    MediaListView(
        modifier = modifier,
        mediaList = musicMap.keys.toList(),
        openMedia = { clickedMedia: Media ->
            playbackController.loadMusic(
                musicMediaItemSortedMap = album.musicMediaItemSortedMap,
                musicToPlay = clickedMedia as Music
            )
            openMedia(media = clickedMedia)
        },
        onFABClick = { openCurrentMusic() },
        header = {
            Header(album = album)
        },
        extraButtons = {
            if (album.musicMediaItemSortedMap.isNotEmpty()) {
                ExtraButton(icon = SatunesIcons.PLAY, onClick = {
                    playbackController.loadMusic(album.musicMediaItemSortedMap)
                    openMedia()
                })
                ExtraButton(icon = SatunesIcons.SHUFFLE, onClick = {
                    playbackController.loadMusic(
                        musicMediaItemSortedMap = album.musicMediaItemSortedMap,
                        shuffleMode = true
                    )
                    openMedia()
                })
            }
        },
        emptyViewText = stringResource(id = R.string.no_music)
    )
}

@Composable
private fun Header(modifier: Modifier = Modifier, album: Album) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        val screenWidthDp = LocalConfiguration.current.screenWidthDp
        val albumSize: Dp = if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
            100.dp
        else if (screenWidthDp < ScreenSizes.VERY_SMALL)
            170.dp
        else 250.dp
        AlbumArtwork(
            modifier = Modifier
                .fillMaxWidth()
                .size(albumSize),
            media = album
        )
        Title(
            bottomPadding = 0.dp,
            text = album.title
        )
        Subtitle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    openMedia(media = album.artist)
                },
            text = album.artist!!.title
        )
    }
}

@Preview
@Composable
private fun AlbumViewPreview() {
    AlbumView(
        album = Album(
            id = 0,
            title = "Album title",
            artist = null,
            musicMediaItemSortedMap = sortedMapOf()
        )
    )
}