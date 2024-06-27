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

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.components.dialog.InformationDialog
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import io.github.antoinepirlot.satunes.ui.views.settings.Settings
import io.github.antoinepirlot.satunes.ui.views.settings.settingsNeedRestart

/**
 *   @author Antoine Pirlot 06/03/2024
 */

@Composable
internal fun SettingWithSwitch(
    modifier: Modifier = Modifier,
    setting: Settings,
    icon: SatunesIcons? = null,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    val isRestartNeeded: Boolean = settingsNeedRestart.contains(setting)
    var showInfo: Boolean by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NormalText(
            text = stringResource(id = setting.stringId),
            maxLines = Int.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth(if (icon != null) 0.7f else 0.75f) // Fix the button to be outside the screen if text is long
        )
        if (icon != null) {
            Icon(imageVector = icon.imageVector, contentDescription = icon.description)
        } else {
            if (isRestartNeeded) {
                @Suppress("NAME_SHADOWING")
                val icon = SatunesIcons.INFO
                Icon(imageVector = icon.imageVector, contentDescription = icon.description)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = {
                if (isRestartNeeded) {
                    showInfo = true
                } else {
                    onCheckedChange()
                }
            }
        )
    }

    if (showInfo) {
        InformationDialog(
            title = stringResource(id = R.string.restart_required),
            onDismissRequest = { showInfo = false },
            onConfirm = {
                onCheckedChange()
                showInfo = false
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
private fun SettingWithSwitchPreview() {
    SettingWithSwitch(
        setting = Settings.PAUSE_IF_ANOTHER_PLAYBACK,
        checked = true,
        icon = SatunesIcons.INFO,
        onCheckedChange = {}
    )
}