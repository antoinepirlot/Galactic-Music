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

package io.github.antoinepirlot.satunes.router

import android.content.Context
import android.net.Uri.decode
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.antoinepirlot.satunes.MainActivity
import io.github.antoinepirlot.satunes.database.models.Album
import io.github.antoinepirlot.satunes.database.models.Artist
import io.github.antoinepirlot.satunes.database.models.Folder
import io.github.antoinepirlot.satunes.database.models.Genre
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.services.DataLoader
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.navController
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.router.utils.openMedia
import io.github.antoinepirlot.satunes.services.RoutesManager
import io.github.antoinepirlot.satunes.ui.views.LoadingView
import io.github.antoinepirlot.satunes.ui.views.album.AlbumView
import io.github.antoinepirlot.satunes.ui.views.album.AllAlbumsListView
import io.github.antoinepirlot.satunes.ui.views.artist.AllArtistsListView
import io.github.antoinepirlot.satunes.ui.views.artist.ArtistView
import io.github.antoinepirlot.satunes.ui.views.folder.FolderView
import io.github.antoinepirlot.satunes.ui.views.folder.RootFolderView
import io.github.antoinepirlot.satunes.ui.views.genre.AllGenresListView
import io.github.antoinepirlot.satunes.ui.views.genre.GenreView
import io.github.antoinepirlot.satunes.ui.views.music.AllMusicsListView
import io.github.antoinepirlot.satunes.ui.views.playback.PlayBackView
import io.github.antoinepirlot.satunes.ui.views.playback.PlaybackQueueView
import io.github.antoinepirlot.satunes.ui.views.playlist.PlaylistListView
import io.github.antoinepirlot.satunes.ui.views.playlist.PlaylistView
import io.github.antoinepirlot.satunes.ui.views.settings.AndroidAutoSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.BatterySettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.BottomNavigationBarSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.ExclusionSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.PermissionsSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.PlaybackSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.PlaylistsSettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.SettingsView
import io.github.antoinepirlot.satunes.ui.views.settings.UpdatesSettingView

/**
 * @author Antoine Pirlot on 23-01-24
 */

@Composable
internal fun Router(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val isLoading: MutableState<Boolean> = rememberSaveable { DataLoader.isLoading }
    val isLoaded: Boolean by rememberSaveable { DataLoader.isLoaded }
    val isAudioAllowed: MutableState<Boolean> =
        rememberSaveable { mutableStateOf(MainActivity.instance.isAudioAllowed()) }

    if (isAudioAllowed.value) {
        LaunchedEffect(key1 = Unit) {
            PlaybackController.initInstance(context = context)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.FOLDERS.link
    ) {

        composable(Destination.FOLDERS.link) {
            // /!\ This route prevent back gesture to exit the app
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                RootFolderView()
            }
        }

        composable("${Destination.FOLDERS.link}/{id}") {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                val folderId = it.arguments!!.getString("id")!!.toLong()
                val folder: Folder by remember {
                    mutableStateOf(
                        DataManager.getFolder(
                            folderId = folderId
                        )
                    )
                }
                FolderView(folder = folder)
            }
        }

        composable(Destination.ARTISTS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                AllArtistsListView()
            }
        }

        composable("${Destination.ARTISTS.link}/{name}") {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                val artistName: String = decode(it.arguments!!.getString("name")!!)
                val artist: Artist by remember {
                    mutableStateOf(
                        DataManager.getArtist(
                            artistName
                        )
                    )
                }
                ArtistView(artist = artist)
            }
        }

        composable(Destination.ALBUMS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                AllAlbumsListView()
            }
        }

        composable("${Destination.ALBUMS.link}/{id}") {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                val albumId: Long = it.arguments!!.getString("id")!!.toLong()
                val album: Album by remember { mutableStateOf(DataManager.getAlbum(albumId)) }
                AlbumView(album = album)
            }
        }

        composable(Destination.GENRES.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                AllGenresListView()
            }
        }

        composable("${Destination.GENRES.link}/{name}") {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                val genreName: String = decode(it.arguments!!.getString("name")!!)
                val genre: Genre by remember { mutableStateOf(DataManager.getGenre(genreName = genreName)) }
                GenreView(genre = genre)
            }
        }

        composable(Destination.PLAYLISTS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                PlaylistListView()
            }
        }

        composable("${Destination.PLAYLISTS.link}/{id}") {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                val playlistId: Long = it.arguments!!.getString("id")!!.toLong()
                val playlist: PlaylistWithMusics by remember {
                    mutableStateOf(DataManager.getPlaylist(playlistId = playlistId))
                }
                PlaylistView(playlist = playlist)
            }
        }

        composable(Destination.MUSICS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                AllMusicsListView()
            }
        }

        composable(Destination.PLAYBACK.link) {
            RoutesManager.currentDestination.value = it.destination.route
            permissionView(isAudioAllowed = isAudioAllowed.value)
            if (isLoading.value || !isLoaded) {
                LoadingView()
            } else {
                PlayBackView(
                    modifier = Modifier.padding(16.dp),
                    onAlbumClick = { album: Album? ->
                        if (album != null) {
                            openMedia(media = album)
                        }
                    },
                    onArtistClick = { artist: Artist ->
                        openMedia(media = artist)
                    }
                )
            }
        }

        composable(Destination.PLAYBACK_QUEUE.link) {
            RoutesManager.currentDestination.value = it.destination.route
            // Here, I assume audio permission is allowed and data has been loaded
            // Also this view will never been accessible if no music is playing
            PlaybackQueueView()
        }

        composable(Destination.SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            SettingsView()
        }

        composable(Destination.BOTTOM_BAR_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            BottomNavigationBarSettingsView()
        }

        composable(Destination.PLAYBACK_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            PlaybackSettingsView()
        }

        composable(Destination.UPDATES_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            UpdatesSettingView()
        }

        composable(Destination.EXCLUSION_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            ExclusionSettingsView()
        }

        composable(Destination.PLAYLISTS_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            PlaylistsSettingsView()
        }

        composable(Destination.PERMISSIONS_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            PermissionsSettingsView(isAudioAllowed = isAudioAllowed)
        }

        composable(Destination.ANDROID_AUTO_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            AndroidAutoSettingsView()
        }

        composable(Destination.BATTERY_SETTINGS.link) {
            RoutesManager.currentDestination.value = it.destination.route
            BatterySettingsView()
        }
    }
}

/**
 * Get the right view depending on is audio allowed.
 *
 * If isAudioAllowed is true, then let the user goes into the app.
 * If isAudioAllowed is false, then force the user to go to permission view
 *
 * @param isAudioAllowed true if the permission has been allowed, otherwise false
 */
private fun permissionView(isAudioAllowed: Boolean) {
    if (!isAudioAllowed) {
        navController.popBackStack()
        navController.navigate(Destination.PERMISSIONS_SETTINGS.link)
    }
}