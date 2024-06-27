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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.github.antoinepirlot.satunes.MainActivity
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus
import io.github.antoinepirlot.satunes.internet.updates.UpdateCheckManager
import io.github.antoinepirlot.satunes.navController
import io.github.antoinepirlot.satunes.router.Destination
import io.github.antoinepirlot.satunes.router.playbackViews
import io.github.antoinepirlot.satunes.router.settingsDestinations
import io.github.antoinepirlot.satunes.services.RoutesManager
import io.github.antoinepirlot.satunes.ui.ScreenSizes

/**
 * @author Antoine Pirlot on 16/01/24
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SatunesTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val barModifier: Modifier =
        if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL) modifier.fillMaxHeight(0.11f) else modifier
    val currentDestination: String? by rememberSaveable { RoutesManager.currentDestination }

    CenterAlignedTopAppBar(
        modifier = barModifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            if (currentDestination !in playbackViews) {
                return@CenterAlignedTopAppBar
            }

            // Here, the user is in the playback view
            IconButton(onClick = { onPlaybackQueueButtonClick() }) {
                val playbackQueueIcon: SatunesIcons = SatunesIcons.PLAYLIST
                Icon(
                    imageVector = playbackQueueIcon.imageVector,
                    contentDescription = playbackQueueIcon.description
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                onClick = { onSettingButtonClick() }
            ) {
                val settingsIcon: SatunesIcons = SatunesIcons.SETTINGS
                Icon(
                    imageVector = settingsIcon.imageVector,
                    contentDescription = settingsIcon.description
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

private fun onPlaybackQueueButtonClick() {
    when (RoutesManager.currentDestination.value) {
        Destination.PLAYBACK.link -> navController.navigate(Destination.PLAYBACK_QUEUE.link)
        Destination.PLAYBACK_QUEUE.link -> navController.navigate(Destination.PLAYBACK.link)
        else -> return
    }
}

/**
 * When currentDestination is the settings list, then return to app only if audio permission has been allowed.
 * Otherwise navigate to settings
 */
private fun onSettingButtonClick() {
    if (UpdateCheckManager.updateAvailableStatus.value != UpdateAvailableStatus.AVAILABLE) {
        UpdateCheckManager.updateAvailableStatus.value =
            UpdateAvailableStatus.UNDEFINED
    }

    when (val currentDestination: String = RoutesManager.currentDestination.value!!) {
        in settingsDestinations -> {
            if (currentDestination == Destination.PERMISSIONS_SETTINGS.link
                && !MainActivity.instance.isAudioAllowed()
            ) {
                return
            } else {
                navController.popBackStack()
                if (navController.currentBackStackEntry == null) {
                    navController.navigate(Destination.FOLDERS.link)
                    navController.navigate(Destination.SETTINGS.link)
                }
            }
        }

        else -> navController.navigate(Destination.SETTINGS.link)
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun SatunesTopAppBarPreview() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    SatunesTopAppBar(
        modifier = Modifier,
        scrollBehavior = scrollBehavior,
    )
}