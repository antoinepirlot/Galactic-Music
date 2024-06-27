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

package io.github.antoinepirlot.satunes.ui.components.images

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.ui.ScreenSizes
import io.github.antoinepirlot.satunes.ui.utils.openUrl

/**
 * @author Antoine Pirlot on 11/04/2024
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ImageWithLink(
    modifier: Modifier = Modifier,
    url: String,
    painterId: Int,
    imageBackgroundColor: Color? = null
) {
    val haptics = LocalHapticFeedback.current
    val context: Context = LocalContext.current
    val screenWidthDp: Int = LocalConfiguration.current.screenWidthDp
    Box(modifier = modifier) {
        val imageSize: Dp = if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
            40.dp
        else
            60.dp
        Image(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .align(Alignment.Center)
                .background(imageBackgroundColor ?: Color.Unspecified)
                .combinedClickable(
                    onClick = { openUrl(context = context, url = url) },
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        Toast
                            .makeText(context, url, Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            painter = painterResource(id = painterId),
            contentDescription = "Tipeee Logo",
        )
    }
}

@Preview
@Composable
private fun ImageWithLinkPreview() {
    ImageWithLink(
        url = "",
        painterId = R.drawable.tipeee_logo
    )
}