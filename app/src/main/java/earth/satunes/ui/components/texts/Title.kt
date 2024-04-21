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

package earth.satunes.ui.components.texts

import android.net.Uri.decode
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Antoine Pirlot on 10/04/2024
 */

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 40.sp,
    textAlign: TextAlign = TextAlign.Center,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val align: Alignment =
        when (textAlign) {
            TextAlign.Center -> Alignment.Center
            TextAlign.Right -> Alignment.CenterEnd
            else -> Alignment.CenterStart
        }
    val textStyle = TextStyle(
        fontWeight = fontWeight,
        textAlign = textAlign,
        fontSize = fontSize
    )
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = decode(text),
            modifier = Modifier
                .align(align)
                .padding(bottom = 16.dp),
            style = textStyle,
        )
    }
}

@Preview
@Composable
fun TitlePreview() {
    Title(text = "Hello World!")
}