package earth.mp3.ui.views

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import earth.mp3.R
import earth.mp3.data.Music
import earth.mp3.ui.components.cards.artists.CardArtistList
import earth.mp3.ui.components.cards.folder.CardFolderList
import earth.mp3.ui.components.cards.menu.CardMenuList
import earth.mp3.ui.components.cards.tracks.CardTrackList

/**
 * Show The Home App Bar and content inside
 */
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier,
    musicList: List<Music>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            val folderSelected = rememberSaveable { mutableStateOf(true) } // default section
            val artistsSelected = rememberSaveable { mutableStateOf(false) }
            val tracksSelected = rememberSaveable { mutableStateOf(false) }
            CardMenuList(
                modifier = modifier,
                folderSelected = folderSelected,
                artistsSelected = artistsSelected,
                tracksSelected = tracksSelected
            )
            //TODO Show folder artists and tracks views from whats selected
            if (folderSelected.value) {
                CardFolderList(modifier = modifier)
            } else if (artistsSelected.value) {
                CardArtistList(modifier = modifier)
            } else if (tracksSelected.value) {
                CardTrackList(modifier = modifier)
            } else {
                throw IllegalStateException(
                    "No tab selected (folder, artists, tracks), that could not happen"
                )
            }
        }
    }
}

/**
 * List that shows "Folders, Artists & Tracks" sections on the top
 */
@Composable
fun HorizontalList(
    modifier: Modifier,
    musicList: List<Music>,
) {
//    LazyRow {
//        itemsIndexed(musicList) {
//            index, item ->
//
//        }
//    }
}

@Composable
@Preview
fun HorizontalListPreview() {
    val music = Music(1, "Il avait les mots", 2, 2, null)
    val musicList = listOf(music)
    HorizontalList(modifier = Modifier.fillMaxSize(), musicList = musicList)
}

//if (musicList.isEmpty()) {
//    Text(text = "The music list is empty, please add music to your phone and restart")
//} else {
//    val player = ExoPlayer.Builder(LocalContext.current).build()
//    val playerControlView = PlayerControlView(LocalContext.current)
//    playerControlView.player = player
//
//    val mediaItem = MediaItem.fromUri(musicList[0].uri)
//    player.setMediaItem(mediaItem)
//    player.prepare()
//    player.play()
//    Text(text = musicList[0].name)
//}