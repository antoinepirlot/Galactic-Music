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

package io.github.antoinepirlot.satunes.ui.views.settings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.services.Permissions
import io.github.antoinepirlot.satunes.services.permissionsList
import io.github.antoinepirlot.satunes.ui.components.settings.Permission
import io.github.antoinepirlot.satunes.ui.components.texts.Title

/**
 * @author Antoine Pirlot on 29/04/2024
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsSettingsView(
    modifier: Modifier = Modifier,
    isAudioAllowed: MutableState<Boolean>,
) {
    val spacerSize = 16.dp
    val context: Context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Title(text = stringResource(id = R.string.permissions))
        val lazySate = rememberLazyListState()
        LazyColumn(
            state = lazySate,
        ) {
            items(
                items = permissionsList,
                key = { it.stringId }
            ) { permission: Permissions ->
                if (
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permission != Permissions.READ_EXTERNAL_STORAGE_PERMISSION)
                    || (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && permission != Permissions.READ_AUDIO_PERMISSION)
                ) {
                    Permission(isAudioAllowed = isAudioAllowed, permission = permission)
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PermissionsSettingsViewPreview() {
    PermissionsSettingsView(isAudioAllowed = mutableStateOf(false))
}