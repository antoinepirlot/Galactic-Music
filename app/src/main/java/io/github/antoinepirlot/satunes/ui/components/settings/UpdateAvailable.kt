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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.internet.R
import io.github.antoinepirlot.satunes.internet.updates.APKDownloadStatus
import io.github.antoinepirlot.satunes.internet.updates.UpdateCheckManager
import io.github.antoinepirlot.satunes.ui.components.LoadingCircle
import io.github.antoinepirlot.satunes.ui.components.buttons.updates.DownloadButton
import io.github.antoinepirlot.satunes.ui.components.buttons.updates.InstallRequestButton
import io.github.antoinepirlot.satunes.ui.components.buttons.updates.SeeDetailsButton
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 14/04/2024
 */

private val SPACER_SIZE = 10.dp

@Composable
internal fun UpdateAvailable(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        NormalText(text = stringResource(id = R.string.update_available))
        Row(verticalAlignment = Alignment.CenterVertically) {
            SeeDetailsButton()
            Spacer(modifier = Modifier.size(SPACER_SIZE))
            val downloadStatus: APKDownloadStatus by remember { UpdateCheckManager.downloadStatus }
            when (downloadStatus) {
                APKDownloadStatus.CHECKING, APKDownloadStatus.DOWNLOADING -> LoadingCircle()
                APKDownloadStatus.DOWNLOADED -> InstallRequestButton()
                APKDownloadStatus.NOT_STARTED -> DownloadButton()
                else -> return
            }
        }
    }
}

@Preview
@Composable
private fun UpdateAvailablePreview() {
    UpdateAvailable()
}