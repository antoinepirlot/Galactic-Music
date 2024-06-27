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
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.antoinepirlot.satunes.database.models.MenuTitle
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.ui.components.cards.ListItem
import io.github.antoinepirlot.satunes.ui.views.settings.Settings

/**
 *   @author Antoine Pirlot 06/03/2024
 */

@Composable
internal fun SettingsSwitchList(
    modifier: Modifier = Modifier,
    checkedMap: Map<Settings, MutableState<Boolean>>,
) {
    val context: Context = LocalContext.current

    Column(modifier = modifier) {
        for (setting: Settings in checkedMap.keys.toList()) {
            ListItem( //Has always horizontal padding of 16.dp
                headlineContent = {
                    SettingWithSwitch(
                        setting = setting,
                        checked = checkedMap[setting]!!.value,
                        onCheckedChange = {
                            switchSetting(
                                context = context,
                                setting = setting
                            )
                        }
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun SettingsSwitchListPreview() {
    SettingsSwitchList(checkedMap = mapOf())
}

private fun switchSetting(context: Context, setting: Settings) {
    when (setting) {
        Settings.FOLDERS_CHECKED -> {
            SettingsManager.switchMenuTitle(
                context = context,
                menuTitle = MenuTitle.FOLDERS
            )
        }

        Settings.ARTISTS_CHECKED -> {
            SettingsManager.switchMenuTitle(
                context = context,
                menuTitle = MenuTitle.ARTISTS
            )
        }

        Settings.ALBUMS_CHECKED -> {
            SettingsManager.switchMenuTitle(
                context = context,
                menuTitle = MenuTitle.ALBUMS
            )
        }

        Settings.GENRES_CHECKED -> {
            SettingsManager.switchMenuTitle(
                context = context,
                menuTitle = MenuTitle.GENRES
            )
        }

        Settings.PLAYLISTS_CHECKED -> {
            SettingsManager.switchMenuTitle(
                context = context,
                menuTitle = MenuTitle.PLAYLISTS
            )
        }

        Settings.PLAYBACK_WHEN_CLOSED -> {
            SettingsManager.switchPlaybackWhenClosedChecked(context = context)
        }

        Settings.PAUSE_IF_NOISY -> {
            SettingsManager.switchPauseIfNoisy(context = context)
        }

        Settings.EXCLUDE_RINGTONES -> {
            SettingsManager.switchExcludeRingtones(context = context)
        }

        Settings.SHUFFLE_MODE -> {
            SettingsManager.switchShuffleMode(context = context)
        }

        Settings.PAUSE_IF_ANOTHER_PLAYBACK -> {
            SettingsManager.switchPauseIfPlayback(context = context)
        }

        else -> { /* Not a switch */
        }
    }
}