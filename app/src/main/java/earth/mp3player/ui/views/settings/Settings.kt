/*
 * This file is part of MP3 Player
 *
 * MP3 Player is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MP3 Player.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 *  ***** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.mp3player.ui.views.settings

import earth.mp3player.R

/**
 *   @author Antoine Pirlot 06/03/2024
 */
enum class Settings(val stringId: Int) {
    CLOSED_APP_PLAYBACK(stringId = R.string.closed_app_playback),
    FOLDERS_CHECKED(stringId = R.string.folders),
    ARTISTS_CHECKED(stringId = R.string.artists),
    ALBUMS_CHECKED(stringId = R.string.albums),
    GENRES_CHECKED(stringId = R.string.genres),
    PAUSE_IF_NOISY(stringId = R.string.pause_if_noisy),
}