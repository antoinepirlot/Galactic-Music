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

package io.github.antoinepirlot.satunes.ui.components.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.ui.components.dialog.InformationDialog
import io.github.antoinepirlot.satunes.ui.views.settings.Settings

/**
 * @author Antoine Pirlot on 27/05/2024
 */

@Composable
internal fun AudioOffloadSetting(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val audioOffloadChecked: Boolean by rememberSaveable { SettingsManager.audioOffloadChecked }
    var showAudioOffloadDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    SettingWithSwitch(
        modifier = modifier,
        setting = Settings.AUDIO_OFFLOAD,
        checked = audioOffloadChecked,
        onCheckedChange = {
            if (audioOffloadChecked) {
                SettingsManager.switchAudioOffload(context = context)
            } else {
                showAudioOffloadDialog = true
            }
        }
    )

    if (showAudioOffloadDialog && !audioOffloadChecked) {
        InformationDialog(
            title = stringResource(id = R.string.audio_offload),
            text = stringResource(id = R.string.audio_offload_info),
            onDismissRequest = { showAudioOffloadDialog = false },
            onConfirm = {
                showAudioOffloadDialog = false
                SettingsManager.switchAudioOffload(context = context)
            }
        )
    }
}

@Preview
@Composable
private fun AudioOffloadSettingPreview() {
    AudioOffloadSetting()
}