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

package io.github.antoinepirlot.satunes.ui.components.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.ui.ScreenSizes
import io.github.antoinepirlot.satunes.ui.components.buttons.playback.NextMusicButton
import io.github.antoinepirlot.satunes.ui.components.buttons.playback.PlayPauseMusicButton
import io.github.antoinepirlot.satunes.ui.components.buttons.playback.PreviousMusicButton
import io.github.antoinepirlot.satunes.ui.components.buttons.playback.RepeatMusicButton
import io.github.antoinepirlot.satunes.ui.components.buttons.playback.ShuffleMusicButton

/**
 * @author Antoine Pirlot on 25/01/24
 */

@Composable
internal fun MusicControlBar(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val ratio: Float =
        if (screenWidthDp >= (ScreenSizes.VERY_VERY_SMALL - 1) && screenWidthDp < ScreenSizes.VERY_SMALL) {
            0.8f
        } else if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL) {
            0.6f
        } else { // Normal
            1f
        }
    val spaceSize = (20f * ratio).dp
    val playPauseButtonSize = (80f * ratio).dp
    val optionButtonSize = (35f * ratio).dp

    Column(modifier = modifier) {
        MusicPositionBar()
        val scrollState: ScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            ShuffleMusicButton(modifier = Modifier.size(optionButtonSize))
            Spacer(modifier = Modifier.width(spaceSize))

            PreviousMusicButton()
            Spacer(modifier = Modifier.width(spaceSize))

            PlayPauseMusicButton(modifier = Modifier.size(playPauseButtonSize))
            Spacer(modifier = Modifier.width(spaceSize))

            NextMusicButton()
            Spacer(modifier = Modifier.width(spaceSize))

            RepeatMusicButton(modifier = Modifier.size(optionButtonSize))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
private fun MediaControlBarPreview() {
    MusicControlBar()
}