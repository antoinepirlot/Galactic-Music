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

package io.github.antoinepirlot.satunes.car.pages

import io.github.antoinepirlot.satunes.database.R

/**
 * @author Antoine Pirlot on 16/03/2024
 */

internal enum class ScreenPages(
    val id: String,
    val description: String,
    val titleId: Int? = null,
) {
    ROOT(id = "root", description = "root page"),
    ALL_FOLDERS(id = "folders", description = "List of all folders", titleId = R.string.folders),
    ALL_ARTISTS(id = "artists", description = "List of all artists", titleId = R.string.artists),
    ALL_ALBUMS(id = "albums", description = "List of all albums", titleId = R.string.albums),
    ALL_GENRES(id = "genres", description = "List of all genres", titleId = R.string.genres),
    ALL_MUSICS(id = "musics", description = "List of all musics", titleId = R.string.musics),
    ALL_PLAYLISTS(
        id = "playlists",
        description = "List of all playlists",
        titleId = R.string.playlists
    )
}

/**
 *  Tabs showed in Android Auto are in the same order as this list, change this list order also
 *  affect the order in Android Auto
 */
internal val pages: List<ScreenPages> = listOf(
    ScreenPages.ALL_PLAYLISTS,
    ScreenPages.ALL_FOLDERS,
//    ScreenPages.ALL_MUSICS,
    ScreenPages.ALL_ARTISTS,
    ScreenPages.ALL_ALBUMS,
    ScreenPages.ALL_GENRES,
)