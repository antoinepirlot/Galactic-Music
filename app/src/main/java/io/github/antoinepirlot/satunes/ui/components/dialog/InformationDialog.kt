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

package io.github.antoinepirlot.satunes.ui.components.dialog

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import io.github.antoinepirlot.satunes.ui.components.texts.Title

/**
 * @author Antoine Pirlot on 27/05/2024
 */

@Composable
internal fun InformationDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String? = null,
    onDismissRequest: () -> Unit,
    onConfirm: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            val infoIcon: SatunesIcons = SatunesIcons.INFO
            Icon(imageVector = infoIcon.imageVector, contentDescription = infoIcon.description)
        },
        title = {
            Title(text = title, fontSize = 25.sp)
        },
        text = {
            if (text != null) {
                val scrollState = rememberScrollState()
                NormalText(
                    modifier = Modifier.verticalScroll(scrollState),
                    text = text,
                    maxLines = Int.MAX_VALUE
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm ?: onDismissRequest) {
                NormalText(text = stringResource(id = R.string.ok))
            }
        },
    )
}

@Preview
@Composable
private fun InformationDialogPreview() {
    InformationDialog(
        title = "Title",
        text = "The text to show on the screen",
        onDismissRequest = {}
    )
}