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

package earth.satunes.ui.views.album

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import earth.satunes.database.models.Album
import earth.satunes.database.models.Media
import earth.satunes.database.models.Music
import earth.satunes.database.services.DataManager
import earth.satunes.playback.services.PlaybackController
import earth.satunes.router.utils.openCurrentMusic
import earth.satunes.router.utils.openMedia
import earth.satunes.router.utils.resetOpenedPlaylist
import earth.satunes.ui.components.buttons.ExtraButton
import earth.satunes.ui.views.MediaListView
import earth.satunes.icons.SatunesIcons
import java.util.SortedMap
import java.util.SortedSet

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
fun AllAlbumsListView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()

    val albumSet: SortedSet<Album> = remember { DataManager.albumSet }

    val musicMediaItemSortedMap: SortedMap<Music, MediaItem> = sortedMapOf()

    albumSet.forEach { album: Album ->
        musicMediaItemSortedMap.putAll(album.musicMediaItemSortedMap)
    }

    resetOpenedPlaylist()
    MediaListView(
        modifier = modifier,
        mediaList = albumSet.toList(),

        openMedia = { clickedMedia: Media ->
            openMedia(navController = navController, media = clickedMedia)
        },
        onFABClick = { openCurrentMusic(navController = navController) },
        extraButtons = {
            ExtraButton(icon = SatunesIcons.SHUFFLE, onClick = {
                playbackController.loadMusic(
                    musicMediaItemSortedMap = musicMediaItemSortedMap,
                    shuffleMode = true
                )
                openMedia(navController = navController)
            })
        }
    )
}

@Preview
@Composable
fun AllAlbumsListViewPreview() {
    AllAlbumsListView(navController = rememberNavController())
}