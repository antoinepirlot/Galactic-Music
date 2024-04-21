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
 * This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.satunes.ui.components.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import earth.satunes.playback.services.PlaybackController
import earth.satunes.ui.components.buttons.music.NextMusicButton
import earth.satunes.ui.components.buttons.music.PreviousMusicButton
import earth.satunes.ui.components.buttons.music.RepeatMusicButton
import earth.satunes.ui.components.buttons.music.ShuffleMusicButton
import earth.satunes.icons.SatunesIcons

/**
 * @author Antoine Pirlot on 25/01/24
 */

@Composable
fun MusicControlBar(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    val playbackController = PlaybackController.getInstance()

    val isPlaying = rememberSaveable { playbackController.isPlaying }

    val spaceBetweenButtons = 20.dp
    val playPauseButtonSize = 80.dp
    val optionButtonSize = 30.dp

    Column {
        MusicPositionBar()
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            ShuffleMusicButton(modifier = Modifier.size(optionButtonSize))
            Spacer(modifier = Modifier.width(spaceBetweenButtons))

            PreviousMusicButton()
            Spacer(modifier = Modifier.width(spaceBetweenButtons))

            IconButton(
                modifier = Modifier.size(playPauseButtonSize),
                onClick = { playbackController.playPause() }
            ) {
                val icon: SatunesIcons =
                    getPlayPauseIconWithDescription(isPlaying = isPlaying.value)

                Icon(
                    modifier = Modifier.size(playPauseButtonSize),
                    imageVector = icon.imageVector,
                    contentDescription = icon.description,
                )
            }

            Spacer(modifier = Modifier.width(spaceBetweenButtons))
            NextMusicButton()

            Spacer(modifier = Modifier.width(spaceBetweenButtons))
            RepeatMusicButton(modifier = Modifier.size(optionButtonSize))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun MediaControlBarPreview() {
    MusicControlBar()
}

private fun getPlayPauseIconWithDescription(isPlaying: Boolean): SatunesIcons {
    return if (isPlaying) {
        SatunesIcons.PAUSE
    } else {
        SatunesIcons.PLAY
    }
}