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
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.services.PermissionManager
import io.github.antoinepirlot.satunes.services.Permissions
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 05/06/2024
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun Permission(
    modifier: Modifier = Modifier,
    isAudioAllowed: MutableState<Boolean>,
    permission: Permissions,
) {
    val context: Context = LocalContext.current
    val spacerSize: Dp = 16.dp
    val permissionState = rememberPermissionState(permission = permission.value)
    if (permissionState.status.isGranted) {
        when (permission) {
            Permissions.READ_AUDIO_PERMISSION -> PermissionManager.isReadAudioAllowed.value =
                true

            Permissions.READ_EXTERNAL_STORAGE_PERMISSION -> PermissionManager.isReadExternalStorageAllowed.value =
                true
        }
        isAudioAllowed.value = true
    }


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = permission.icon.imageVector,
            contentDescription = permission.icon.description
        )
        Spacer(modifier = Modifier.size(spacerSize))
        NormalText(text = stringResource(id = permission.stringId))
        Spacer(modifier = Modifier.size(spacerSize))

        val permissionGranted: Boolean by remember {
            when (permission) {
                Permissions.READ_AUDIO_PERMISSION -> PermissionManager.isReadAudioAllowed
                Permissions.READ_EXTERNAL_STORAGE_PERMISSION -> PermissionManager.isReadExternalStorageAllowed
            }
        }

        if (permissionGranted) {
            val icon: SatunesIcons = SatunesIcons.PERMISSION_GRANTED
            Icon(
                imageVector = icon.imageVector,
                contentDescription = icon.description,
                tint = Color.Green
            )
        } else {
            val icon: SatunesIcons = SatunesIcons.PERMISSION_NOT_GRANTED
            Icon(
                imageVector = icon.imageVector,
                contentDescription = icon.description,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.size(spacerSize))
            Button(onClick = {
                if (permissionState.status.shouldShowRationale) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                } else {
                    permissionState.launchPermissionRequest()
                }
            }) {
                NormalText(text = stringResource(id = R.string.ask_permission))
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PermissionPreview() {
    Permission(
        isAudioAllowed = mutableStateOf(false),
        permission = Permissions.READ_AUDIO_PERMISSION
    )
}