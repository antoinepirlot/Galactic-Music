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

package io.github.antoinepirlot.satunes.ui.components.cards.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.antoinepirlot.satunes.database.models.Album
import io.github.antoinepirlot.satunes.ui.ScreenSizes
import io.github.antoinepirlot.satunes.ui.components.images.AlbumArtwork
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText

/**
 * @author Antoine Pirlot on 11/04/2024
 */

@Composable
internal fun AlbumGridCard(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: (album: Album?) -> Unit,
) {
    val screenWidthDp: Int = LocalConfiguration.current.screenWidthDp

    val boxSize: Dp = if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
        150.dp
    else if (screenWidthDp < ScreenSizes.VERY_SMALL)
        200.dp
    else
        250.dp

    val artworkSize: Dp = if (screenWidthDp < ScreenSizes.VERY_VERY_SMALL)
        125.dp
    else if (screenWidthDp < ScreenSizes.VERY_SMALL)
        175.dp
    else
        225.dp

    Box(modifier = modifier
        .padding(start = 16.dp)
        .size(boxSize)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlbumArtwork(
                modifier.size(artworkSize),
                media = album,
                onClick = onClick
            )
            NormalText(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                text = album.title
            )
        }
    }
}

@Preview
@Composable
private fun AlbumGridCardPreview() {
    AlbumGridCard(album = Album(id = 1, title = "Album #1"), onClick = {})
}