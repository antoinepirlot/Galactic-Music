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

package io.github.antoinepirlot.satunes.ui.views.settings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.ui.components.settings.AudioOffloadSetting
import io.github.antoinepirlot.satunes.ui.components.settings.BarSpeedSetting
import io.github.antoinepirlot.satunes.ui.components.settings.PlaybackModesSubSettings
import io.github.antoinepirlot.satunes.ui.components.settings.SettingsSwitchList
import io.github.antoinepirlot.satunes.ui.components.settings.SubSetting
import io.github.antoinepirlot.satunes.ui.components.texts.Title

/**
 *   @author Antoine Pirlot 06/03/2024
 */

@Composable
internal fun PlaybackSettingsView(
    modifier: Modifier = Modifier
) {
    val checkedMap: Map<Settings, MutableState<Boolean>> = mapOf(
        Pair(
            first = Settings.PLAYBACK_WHEN_CLOSED,
            second = SettingsManager.playbackWhenClosedChecked
        ),
        Pair(
            first = Settings.PAUSE_IF_NOISY,
            second = SettingsManager.pauseIfNoisyChecked
        ),
        Pair(
            first = Settings.PAUSE_IF_ANOTHER_PLAYBACK,
            second = SettingsManager.pauseIfAnotherPlayback
        )
    )

    val scrollState: ScrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        Title(text = stringResource(id = R.string.playback_settings))
        SubSetting {
            SettingsSwitchList(checkedMap = checkedMap) //Contains list item so always padding horizontal 16.dp
            BarSpeedSetting(modifier = Modifier.padding(horizontal = 16.dp))
        }
        PlaybackModesSubSettings() //Contains list item so always padding horizontal 16.dp
        SubSetting(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.battery_settings)
        ) {
            AudioOffloadSetting() //Contains list item so always padding horizontal 16.dp
        }
    }
}

@Composable
@Preview
private fun PlaybackSettingsViewPreview() {
    PlaybackSettingsView()
}