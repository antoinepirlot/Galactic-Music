package earth.mp3.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import earth.mp3.models.Artist
import earth.mp3.models.Folder
import earth.mp3.models.Media
import earth.mp3.models.Music
import earth.mp3.ui.PlayBackView
import earth.mp3.ui.components.cards.MediaCardList

@Composable
fun Router(
    modifier: Modifier = Modifier,
    startDestination: String,
    rootFolderList: List<Folder>,
    artistListToShow: MutableList<Artist>,
    musicListToShow: MutableList<Music>,
    folderMap: Map<Long, Folder>
) {
    val listToShow: MutableList<Media> = remember { mutableListOf() }

    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.FOLDERS.link) {
            // /!\ This line prevent back gesture to exit the app
            MediaCardList(
                mediaList = rootFolderList,
                openMedia = { clickedMedia: Media ->
                    navController.navigate(getDestinationOf(clickedMedia))
                }
            )
        }

        composable("${Destination.FOLDERS.link}/{id}") {
            val folderId = it.arguments!!.getString("id")!!.toLong()
            var folder: Folder = folderMap[Music.FIRST_FOLDER_INDEX]!!
            listToShow.clear()

            if (folderId >= Music.FIRST_FOLDER_INDEX && folderId <= folderMap.size) {
                folder = folderMap[folderId]!!
                val listToAdd = folder.getSubFolderList()
                listToShow.addAll(listToAdd)
            } else {
                listToShow.addAll(rootFolderList)
            }
            if (folder.musicList.isNotEmpty()) {
                listToShow.addAll(folder.musicList.toMutableList())
            }

            MediaCardList(
                mediaList = listToShow,
                openMedia = { clickedMedia: Media ->
                    navController.navigate(getDestinationOf(clickedMedia))
                }
            )
        }

        composable(Destination.ARTISTS.link) {
            MediaCardList(
                mediaList = artistListToShow,
                openMedia = { clickedMedia: Media ->
                    navController.navigate(getDestinationOf(clickedMedia))
                }
            )
        }

        composable(Destination.MUSICS.link) {
            MediaCardList(
                mediaList = musicListToShow,
                openMedia = { clickedMedia: Media ->
                    navController.navigate(getDestinationOf(clickedMedia))
                }
            )
        }

        composable(Destination.PLAYBACK.link) {
            PlayBackView()
        }
    }
}

/**
 * Return the destination link of media (folder, artists or music) with its id.
 * For example if media is folder, it returns: /folders/5
 *
 * @param media the media to get the destination link
 *
 * @return the media destination link with the media's id
 */
private fun getDestinationOf(media: Media): String {
    return when (media) {
        is Folder -> {
            "${Destination.FOLDERS.link}/${media.id}"
        }

        is Artist -> {
            "${Destination.ARTISTS.link}/${media.id}"
        }

        else -> {
            "${Destination.MUSICS.link}/${media.id}"
        }
    }
}