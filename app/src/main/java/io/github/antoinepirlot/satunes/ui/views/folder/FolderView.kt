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

package io.github.antoinepirlot.satunes.ui.views.folder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Folder
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.icons.SatunesIcons
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openCurrentMusic
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.router.utils.openMediaFromFolder
import io.github.antoinepirlot.satunes.ui.components.buttons.ExtraButton
import io.github.antoinepirlot.satunes.ui.components.texts.Title
import io.github.antoinepirlot.satunes.ui.utils.getRootFolderName
import io.github.antoinepirlot.satunes.ui.views.MediaListView
import java.util.SortedMap
import java.util.SortedSet

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
internal fun FolderView(
    modifier: Modifier = Modifier,
    folder: Folder,
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()
    val folderMusicMediaItemSortedMap: SortedMap<Music, MediaItem> = remember {
        folder.musicMediaItemSortedMap
    }

    //Recompose if data changed
    var mapChanged: Boolean by rememberSaveable { folder.musicMediaItemSortedMapUpdate }
    if (mapChanged) {
        mapChanged = false
    }
    //

    val subFolderMap: SortedSet<Media> = sortedSetOf()

    Column(modifier = modifier) {
        if (folder.parentFolder == null) {
            Title(
                text = '/' + getRootFolderName(title = folder.title),
                fontSize = 20.sp,
                maxLines = 2
            )
        } else {
            val allPath: MutableList<String> = folder.absolutePath.split("/").toMutableList()
            allPath.removeFirst()
            allPath[0] = getRootFolderName(title = allPath[0])
            var path = ""
            for (s: String in allPath) {
                path += "/${s}"
            }
            Title(text = path, fontSize = 20.sp, maxLines = 2)
        }
        loadSubFolders(
            subFolderMap = subFolderMap,
            folder = folder,
            folderMusicMediaItemSortedMap = folderMusicMediaItemSortedMap
        )
        MediaListView(
            mediaList = subFolderMap.toList(),
            openMedia = { clickedMedia: Media ->
                openMediaFromFolder(clickedMedia)
            },
            onFABClick = { openCurrentMusic() },
            extraButtons = {
                val folderMusics: SortedMap<Music, MediaItem> = folder.getAllMusic()
                if (folderMusics.isNotEmpty()) {
                    ExtraButton(icon = SatunesIcons.PLAY, onClick = {
                        playbackController.loadMusic(musicMediaItemSortedMap = folderMusics)
                        openMedia()
                    })
                    ExtraButton(icon = SatunesIcons.SHUFFLE, onClick = {
                        playbackController.loadMusic(
                            musicMediaItemSortedMap = folderMusics,
                            shuffleMode = true
                        )
                        openMedia()
                    })
                }
            },
            emptyViewText = stringResource(id = R.string.no_music)
        )
    }
}

private fun loadSubFolders(
    subFolderMap: SortedSet<Media>,
    folder: Folder,
    folderMusicMediaItemSortedMap: SortedMap<Music, MediaItem>
) {
    //Load sub-folders
    subFolderMap.addAll(folder.getSubFolderMapAsMedia().values)

    //Load sub-folder's musics
    folderMusicMediaItemSortedMap.forEach { (music: Music, _) ->
        subFolderMap.add(music)
    }
}

@Preview
@Composable
private fun FolderViewPreview() {
    FolderView(folder = Folder(id = 0, title = "Folder title"))
}