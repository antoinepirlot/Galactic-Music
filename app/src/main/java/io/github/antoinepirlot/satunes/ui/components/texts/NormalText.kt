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

package io.github.antoinepirlot.satunes.ui.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.antoinepirlot.satunes.ui.ScreenSizes

/**
 * @author Antoine Pirlot on 20/04/2024
 */

@Composable
internal fun NormalText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    val screenWidthDp: Int = LocalConfiguration.current.screenWidthDp
    Text(
        modifier = modifier,
        text = text,
        fontSize = if (fontSize != TextUnit.Unspecified && screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
            fontSize / 2
        else if (fontSize == TextUnit.Unspecified && screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
            10.sp
        else if (fontSize != TextUnit.Unspecified && screenWidthDp < ScreenSizes.VERY_SMALL)
            fontSize / 1.5
        else fontSize,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
    )
}