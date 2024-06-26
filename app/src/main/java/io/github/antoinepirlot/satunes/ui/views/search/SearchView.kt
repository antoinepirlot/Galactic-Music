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

package io.github.antoinepirlot.satunes.ui.views.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.services.search.SearchChips
import io.github.antoinepirlot.satunes.services.search.SearchChipsManager
import io.github.antoinepirlot.satunes.ui.components.cards.media.MediaCardList
import io.github.antoinepirlot.satunes.ui.components.chips.MediaChipList
import io.github.antoinepirlot.satunes.ui.components.texts.NormalText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author Antoine Pirlot on 27/06/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchView(
    modifier: Modifier = Modifier
) {
    var query: String by rememberSaveable { mutableStateOf("") }
    val mediaList: MutableList<Media> = remember { SnapshotStateList() }
    val selectedSearchChips: List<SearchChips> = remember { SearchChipsManager.selectedSearchChips }

    val searchCoroutine: CoroutineScope = rememberCoroutineScope()
    var searchJob: Job? = null
    LaunchedEffect(key1 = query, key2 = selectedSearchChips.size) {
        if (searchJob != null && searchJob!!.isActive) {
            searchJob!!.cancel()
        }
        searchJob = searchCoroutine.launch {
            search(mediaList = mediaList, query = query)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { query = it },
            active = false,
            onActiveChange = { /* Do not use active mode */ },
            placeholder = { NormalText(text = stringResource(id = R.string.search_placeholder)) },
            content = { /* Content if active is true but never used */ }
        )
        Spacer(modifier = Modifier.size(16.dp))
        MediaChipList()
        Content(mediaList = mediaList)
    }
}

@Composable
private fun Content(mediaList: List<Media>) {
    if (mediaList.isEmpty()) {
        NormalText(text = stringResource(id = R.string.no_result))
    } else {
        MediaCardList(mediaList = mediaList, openMedia = { media: Media ->
            if (media is Music) {
                PlaybackController.getInstance()
                    .loadMusic(musicMediaItemSortedMap = DataManager.musicMediaItemSortedMap)
            }
            openMedia(media = media)
        })
    }
}

private fun search(mediaList: MutableList<Media>, query: String) {
    mediaList.clear()
    if (query.isBlank()) {
        // Prevent loop if string is "" or " "
        return
    }
    for (searchChip: SearchChips in SearchChipsManager.selectedSearchChips) {
        DataManager.musicMediaItemSortedMap.keys.forEach { music: Music ->
            when (searchChip) {
                SearchChips.MUSICS -> {
                    if (music.title.lowercase().contains(query.lowercase())) {
                        if (!mediaList.contains(music)) {
                            mediaList.add(element = music)
                        }
                    }
                }

                SearchChips.ARTISTS -> {
                    if (music.artist.title.lowercase().contains(query.lowercase())) {
                        if (!mediaList.contains(music.artist)) {
                            mediaList.add(element = music.artist)
                        }
                    }
                }

                SearchChips.ALBUMS -> {
                    if (music.album.title.lowercase().contains(query.lowercase())) {
                        if (!mediaList.contains(music.album)) {
                            mediaList.add(element = music.album)
                        }
                    }
                }

                SearchChips.GENRES -> {
                    if (music.genre.title.lowercase().contains(query.lowercase())) {
                        if (!mediaList.contains(music.genre)) {
                            mediaList.add(element = music.genre)
                        }
                    }
                }

                SearchChips.FOLDERS -> {
                    if (music.folder.title.lowercase().contains(query.lowercase())) {
                        if (!mediaList.contains(music.folder)) {
                            mediaList.add(element = music.folder)
                        }
                    }
                }
            }
        }
    }
    mediaList.sort()
}

@Preview
@Composable
private fun SearchViewPreview() {
    SearchView()
}