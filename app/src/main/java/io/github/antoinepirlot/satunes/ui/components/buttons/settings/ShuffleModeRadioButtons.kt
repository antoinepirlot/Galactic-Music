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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.car.R
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import io.github.antoinepirlot.satunes.ui.utils.getRightIconColors
import io.github.antoinepirlot.satunes.ui.utils.getRightIconTintColor

/**
 * @author Antoine Pirlot on 05/06/2024
 */

@Composable
fun ShuffleModeRadioButtons(
    modifier: Modifier = Modifier
) {
    val iconList: List<SatunesIcons> = listOf(
        SatunesIcons.SHUFFLE, // i = 0 or false
        SatunesIcons.SHUFFLE// i = 1 or true
    )

    val state: Boolean by rememberSaveable { SettingsManager.shuffleMode }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NormalText(text = stringResource(id = R.string.shuffle) + ':')
        for (i: Int in iconList.indices) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val isShuffleOnIcon: Boolean =
                    i > 0 // If i is 0 then it is shuffle off otherwise shuffle on
                val context: Context = LocalContext.current
                val icon: SatunesIcons = iconList[i]

                RadioButton(
                    selected = state == isShuffleOnIcon,
                    onClick = {
                        SettingsManager.switchShuffleMode(context = context)
                    }
                )

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = getRightIconColors(isOn = isShuffleOnIcon).containerColor),
                    imageVector = icon.imageVector,
                    contentDescription = icon.description,
                    tint = getRightIconTintColor(isOn = isShuffleOnIcon)
                )
            }
        }
    }
}

@Preview
@Composable
fun ShuffleModeRadioButtonPreview() {
    ShuffleModeRadioButtons()
}