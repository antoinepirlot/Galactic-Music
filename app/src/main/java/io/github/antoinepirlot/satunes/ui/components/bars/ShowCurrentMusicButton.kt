/*
 * This file is part of Satunes.
 *
 * Satunes is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Satunes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Satunes.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * **** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/Satunes
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package io.github.antoinepirlot.satunes.ui.components.bars

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.ScreenSizes

/**
 * @author Antoine Pirlot on 3/02/24
 */

@Composable
internal fun ShowCurrentMusicButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val screenWidthDp: Int = LocalConfiguration.current.screenWidthDp
    val buttonSize: Dp = if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
        80.dp
    else
        100.dp
    LargeFloatingActionButton(
        modifier = modifier.size(buttonSize),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(buttonSize / 1.5f),
            imageVector = SatunesIcons.MUSIC.imageVector,
            contentDescription = "Show Current Music Icon"
        )
    }
}

@Composable
@Preview
private fun ShowCurrentMusicPreview() {
    ShowCurrentMusicButton(onClick = {})
}