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

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus.AVAILABLE
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus.CANNOT_CHECK
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus.UNDEFINED
import io.github.antoinepirlot.satunes.internet.updates.UpdateAvailableStatus.UP_TO_DATE
import io.github.antoinepirlot.satunes.internet.updates.UpdateCheckManager
import io.github.antoinepirlot.satunes.internet.updates.UpdateCheckManager.getCurrentVersion
import io.github.antoinepirlot.satunes.ui.components.LoadingCircle
import io.github.antoinepirlot.satunes.ui.components.buttons.updates.CheckUpdateButton
import io.github.antoinepirlot.satunes.ui.components.settings.UpdateAvailable
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import io.github.antoinepirlot.satunes.ui.components.texts.Title

/**
 * @author Antoine Pirlot on 11/04/2024
 */

@Composable
internal fun UpdatesSettingView(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val currentVersion = getCurrentVersion(context = context)
    val isCheckingUpdate: Boolean by rememberSaveable { UpdateCheckManager.isCheckingUpdate }
    val updateAvailable: UpdateAvailableStatus by remember { UpdateCheckManager.updateAvailableStatus }
    val scrollState: ScrollState = rememberScrollState()

    Column(modifier = modifier
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp)) {
        Title(text = stringResource(id = R.string.version))
        NormalText(text = stringResource(id = R.string.current_version) + currentVersion)
        //Check update is done when pressing setting button in top app bar
        if (isCheckingUpdate) {
            LoadingCircle()
            return
        }

        when (updateAvailable) {
            UNDEFINED, CANNOT_CHECK, UP_TO_DATE -> CheckUpdateButton()
            AVAILABLE -> UpdateAvailable()
        }
    }
}

@Preview
@Composable
private fun VersionViewPreview() {
    UpdatesSettingView()
}