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

package io.github.antoinepirlot.satunes.car.playback

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.MediaSessionCompat.QueueItem
import android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED
import android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING
import androidx.core.graphics.drawable.toBitmap
import androidx.media.MediaBrowserServiceCompat
import io.github.antoinepirlot.satunes.car.R
import io.github.antoinepirlot.satunes.car.pages.ScreenPages
import io.github.antoinepirlot.satunes.car.pages.pages
import io.github.antoinepirlot.satunes.car.utils.buildMediaItem
import io.github.antoinepirlot.satunes.database.models.Media
import io.github.antoinepirlot.satunes.database.models.Music
import io.github.antoinepirlot.satunes.database.services.DataLoader
import io.github.antoinepirlot.satunes.database.services.DataManager
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import io.github.antoinepirlot.satunes.icons.R as RIcons

/**
 * @author Antoine Pirlot on 16/03/2024
 */
internal class SatunesCarMusicService : MediaBrowserServiceCompat() {

    private lateinit var playbackController: PlaybackController

    companion object {
        val routeDeque: RouteDeque = RouteDeque()
        lateinit var session: MediaSessionCompat

        private val loadedQueueItemList: MutableList<QueueItem> = mutableListOf()

        fun updateQueue() {
            session.setQueue(loadedQueueItemList)
        }

        /**
         * Add the media to the queue by creating a media item.
         *
         * @return the newly created media item.
         */
        internal fun addToQueue(media: Media): MediaItem {
            val mediaItem: MediaItem = buildMediaItem(media = media)
            if (media is Music) {
                val queueItem = QueueItem(mediaItem.description, media.id)
                loadedQueueItemList.add(queueItem)
            }
            return mediaItem
        }
    }

    override fun onCreate() {
        super.onCreate()

        val className: String = this.javaClass.name.split(".").last()
        session = MediaSessionCompat(this, className)
        sessionToken = session.sessionToken
        session.setCallback(SatunesCarCallBack)

        routeDeque.resetRouteDeque()

        //Init playback
        playbackController =
            PlaybackController.initInstance(baseContext, listener = SatunesPlaybackListener)
        while (DataLoader.isLoading.value) {
            //Wait
        }
        if (DataLoader.isLoaded.value) {
            loadAllPlaybackData()
        }
    }

    private fun loadAllPlaybackData() {
        val playbackController: PlaybackController = PlaybackController.getInstance()
        SatunesPlaybackListener.updateMediaPlaying()
        if (playbackController.isPlaying.value) {
            SatunesPlaybackListener.updatePlaybackState(
                state = STATE_PLAYING,
                actions = SatunesCarCallBack.ACTIONS_ON_PLAY
            )
        } else {
            SatunesPlaybackListener.updatePlaybackState(
                state = STATE_PAUSED,
                actions = SatunesCarCallBack.ACTIONS_ON_PAUSE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        session.release()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(ScreenPages.ROOT.id, null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaItem>>) {
        val children: MutableList<MediaItem> = mutableListOf()
        when (parentId) {
            ScreenPages.ROOT.id -> {
                routeDeque.resetRouteDeque()
                children.addAll(getHomeScreenBars())
            }

            ScreenPages.ALL_FOLDERS.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.folderSortedMap.keys))
            }

            ScreenPages.ALL_ARTISTS.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.artistMap.values))
            }

            ScreenPages.ALL_ALBUMS.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.albumSet))
            }

            ScreenPages.ALL_GENRES.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.genreMap.values))
            }

            ScreenPages.ALL_MUSICS.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.musicMediaItemSortedMap.keys))
            }

            ScreenPages.ALL_PLAYLISTS.id -> {
                routeDeque.resetRouteDeque()
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaList = DataManager.playlistWithMusicsMap.values))
            }

            else -> {
                //When a music is selected, loadChildren is not called, so it's never a music
                if (routeDeque.isEmpty()) {
                    result.sendResult(null)
                    return
                }
                routeDeque.addLast(parentId)
                children.addAll(getAllMediaItem(mediaId = parentId.toLong()))
            }
        }
        result.sendResult(children)
    }

    private fun getShuffleButton(): MediaItem {
        val icon: Bitmap = this.getDrawable(RIcons.drawable.white_shuffle_off)!!.toBitmap()

        return buildMediaItem(
            id = "shuffle",
            description = "Shuffle Button",
            title = this.getString(R.string.shuffle),
            icon = icon,
            flags = MediaItem.FLAG_PLAYABLE
        )
    }

    private fun getHomeScreenBars(): MutableList<MediaItem> {
        val children: MutableList<MediaItem> = mutableListOf()
        for (page: ScreenPages in pages) {
            val mediaItem: MediaItem = buildMediaItem(
                id = page.id,
                description = page.description,
                title = if (page.titleId == null) ScreenPages.ROOT.id else this.getString(page.titleId),
                flags = MediaItem.FLAG_BROWSABLE
            )
            children.add(mediaItem)
        }
        return children
    }

    /**
     * Get a list of media item based on the media.
     *
     * It creates all MediaItem from all media, if it is a music then it is playable, otherwise
     * it is browsable.
     *
     * Also add these media items as queue item to prepare the queue (don't apply).
     *
     * @param mediaList the media list that contains all media to transform to MediaItem
     *
     * @return a mutable list of MediaItem
     */
    private fun getAllMediaItem(mediaList: Collection<Media>): MutableList<MediaItem> {
        val mediaItemList: MutableList<MediaItem> = mutableListOf()
        loadedQueueItemList.clear()
        if (mediaList.isEmpty()) {
            return mediaItemList
        }
        mediaItemList.add(getShuffleButton())
        for (media: Media in mediaList) {
            if (media !is Music && media.musicMediaItemSortedMap.isEmpty()) {
                continue
            }
            val mediaItem: MediaItem = addToQueue(media = media)
            mediaItemList.add(mediaItem)
        }
        return mediaItemList
    }

    /**
     * Get the media from the media referenced, assume that mediaId is never the id of a music.
     * When the route is in pages, then load musics of the right media.
     *
     * @param mediaId the media referenced, it's the media id.
     *
     * @return a mutable list of media item.
     */
    private fun getAllMediaItem(mediaId: Long): MutableList<MediaItem> {
        val oneBeforeLastRoute: String = routeDeque.oneBeforeLast()
        if (oneBeforeLastRoute == ScreenPages.ROOT.id || oneBeforeLastRoute == ScreenPages.ALL_MUSICS.id) {
            throw IllegalStateException("An error occurred in the route processing")
        }

        val media: Media? = when (oneBeforeLastRoute) {
            ScreenPages.ALL_FOLDERS.id -> DataManager.getFolder(folderId = mediaId)
            ScreenPages.ALL_ARTISTS.id -> DataManager.getArtist(artistId = mediaId)
            ScreenPages.ALL_ALBUMS.id -> DataManager.getAlbum(albumId = mediaId)
            ScreenPages.ALL_GENRES.id -> DataManager.getGenre(genreId = mediaId)
            ScreenPages.ALL_PLAYLISTS.id -> DataManager.getPlaylist(playlistId = mediaId)
            else -> null
        }

        val listToReturn: MutableList<MediaItem> = mutableListOf()
        listToReturn.addAll(
            this.getAllMediaItem(
                mediaList = media?.musicMediaItemSortedMap?.keys?.toList() ?: mutableListOf()
            )
        )
        return listToReturn
    }
}