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

package io.github.antoinepirlot.satunes.ui.components.buttons.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.ScreenSizes
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import io.github.antoinepirlot.satunes.ui.utils.getRightIconColors
import io.github.antoinepirlot.satunes.ui.utils.getRightIconTintColor

/**
 * @author Antoine Pirlot on 13/05/2024
 */

@Composable
internal fun RepeatModeRadioButtons(
    modifier: Modifier = Modifier,
) {
    val iconsList: List<SatunesIcons> = listOf(
        SatunesIcons.REPEAT, // i = 0
        SatunesIcons.REPEAT, // i = 1
        SatunesIcons.REPEAT_ONE // i = 2
    )
    var state: Int by remember { SettingsManager.repeatMode }

    val screenWidthDp: Int = LocalConfiguration.current.screenWidthDp
    val radioButtonModifier: Modifier =
        if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
            Modifier.size(15.dp)
        else if (screenWidthDp < ScreenSizes.NORMAL)
            Modifier.size(20.dp)
        else Modifier

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NormalText(text = stringResource(id = R.string.repeat_mode))
        for (i: Int in iconsList.indices) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val context: Context = LocalContext.current
                val onClick: () -> Unit = {
                    state = i
                    SettingsManager.updateRepeatMode(context = context, newValue = i)
                }

                RadioButton(
                    modifier = radioButtonModifier,
                    selected = state == i,
                    onClick = onClick
                )
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = getRightIconColors(isOn = i > 0).containerColor),
                    imageVector = iconsList[i].imageVector,
                    contentDescription = iconsList[i].description,
                    tint = getRightIconTintColor(isOn = i > 0)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RepeatModeRadioButtonsPreview() {
    RepeatModeRadioButtons()
}