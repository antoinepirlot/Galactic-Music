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

package earth.satunes.ui.views.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import earth.satunes.R
import earth.satunes.ui.components.settings.Facebook
import earth.satunes.ui.components.settings.GitHub
import earth.satunes.ui.components.settings.Tipeee
import earth.satunes.ui.components.texts.NormalText
import earth.satunes.ui.components.texts.Title

/**
 * @author Antoine Pirlot on 10/04/2024
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutView(
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        Title(text = stringResource(id = R.string.about), fontSize = 20.sp)
        NormalText(
            text = stringResource(id = R.string.about_text),
            maxLines = Int.MAX_VALUE
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Tipeee()
            Facebook()
            GitHub()
        }
    }
}

@Preview
@Composable
fun AboutViewPreview() {
    AboutView()
}