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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Antoine Pirlot on 22/05/2024
 */

/**
 * Get right Icon Button Colors matching system dark mode.
 */
@Composable
internal fun getRightIconColors(isOn: Boolean): IconButtonColors {
    return IconButtonDefaults.iconButtonColors(
        containerColor =
        if (isOn) {
            if (!isSystemInDarkTheme()) {
                Color.Black
            } else {
                Color.White
            }
        } else {
            Color.Unspecified // No color for background
        },
    )
}

/**
 * Get Right tint color matching system dark mode.
 */
@Composable
internal fun getRightIconTintColor(isOn: Boolean): Color {
    return if (isOn) {
        if (!isSystemInDarkTheme()) {
            Color.White
        } else {
            Color.Black
        }
    } else {
        if (!isSystemInDarkTheme()) {
            Color.Black
        } else {
            Color.White
        }
    }
}