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

package earth.satunes.ui.utils

import java.util.concurrent.TimeUnit

/**
 * @author Antoine Pirlot on 23/02/24
 */

fun getMillisToTimeText(milliseconds: Long): String {
    var toReturn = ""
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    if (hours > 0) {
        toReturn += "$hours:"
    }
    var minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    minutes -= hours * 60
    if (minutes < 10) {
        toReturn += "0"
    }
    toReturn += "$minutes:"
    var seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    seconds -= minutes * 60
    if (seconds < 10) {
        toReturn += "0"
    }
    return toReturn + "$seconds"
}