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

package io.github.antoinepirlot.satunes.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.media3.common.MediaItem
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Folder
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.MenuTitle
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.icons.SatunesIcons.ALBUM
import io.github.antoinepirlot.satunes.icons.SatunesIcons.ARTIST
import io.github.antoinepirlot.satunes.icons.SatunesIcons.FOLDER
import io.github.antoinepirlot.satunes.icons.SatunesIcons.GENRES
import io.github.antoinepirlot.satunes.icons.SatunesIcons.MUSIC
import io.github.antoinepirlot.satunes.icons.SatunesIcons.PLAYLIST
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 27/01/24
 */

/**
 * Start the music
 *
 * @param mediaToPlay the music to play from the music list
 */

internal fun startMusic(mediaToPlay: Media? = null) {
    val playbackController = PlaybackController.getInstance()

    when (mediaToPlay) {
        is Music -> {
            playbackController.start(mediaToPlay)
        }

        is Folder -> {
            playbackController.start()
        }

        null -> {
            playbackController.start()
        }
    }
}

/**
 * Create the list of all music in the folder and subfolder
 */
fun getMusicListFromFolder(folder: Folder): SortedMap<Music, MediaItem> {
    val mapOfMusic: SortedMap<Music, MediaItem> = folder.musicMediaItemSortedMap
    for (subfolder in folder.getSubFolderList().values) {
        mapOfMusic.putAll(subfolder.musicMediaItemSortedMap)
    }
    return mapOfMusic
}

fun getRightIconAndDescription(menuTitle: MenuTitle): Pair<ImageVector, String> {
    return when (menuTitle) {
        MenuTitle.FOLDERS -> FOLDER.imageVector to FOLDER.description

        MenuTitle.ARTISTS -> ARTIST.imageVector to ARTIST.description

        MenuTitle.ALBUMS -> ALBUM.imageVector to ALBUM.description

        MenuTitle.GENRES -> GENRES.imageVector to GENRES.description

        MenuTitle.PLAYLISTS -> PLAYLIST.imageVector to PLAYLIST.description

        else -> MUSIC.imageVector to MUSIC.description
    }
}

/**
 * Return the root folder name: 0 -> This device, else -> External Storage: name
 */
@Composable
fun getRootFolderName(title: String): String {
    return when (title) {
        "0" -> stringResource(id = R.string.this_device)

        else -> "${stringResource(id = R.string.external_storage)}: $title"
    }
}