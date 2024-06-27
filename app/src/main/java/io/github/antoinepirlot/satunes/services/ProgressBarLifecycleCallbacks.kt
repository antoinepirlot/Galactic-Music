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

package io.github.antoinepirlot.satunes.services

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.playback.services.PlaybackController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Antoine Pirlot on 02/05/2024
 */
internal object ProgressBarLifecycleCallbacks : DefaultLifecycleObserver {
    var isUpdatingPosition: Boolean = false
    private var stopRefresh: Boolean = false

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        startUpdatingCurrentPosition()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        stopRefresh = true
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stopRefresh = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopRefresh = true
    }

    fun startUpdatingCurrentPosition() {
        stopRefresh = false
        refreshCurrentPosition()
    }

    /**
     * Launch a coroutine where the currentPositionProgression is updated every 1 second.
     * If this function is already running, just return by using isUpdatingPosition.
     */
    private fun refreshCurrentPosition() {
        val playbackController: PlaybackController = PlaybackController.getInstance()
        if (isUpdatingPosition || playbackController.musicPlaying.value == null) {
            return
        }
        isUpdatingPosition = true
        CoroutineScope(Dispatchers.Main).launch {
            updateCurrentPosition()
            while (playbackController.isPlaying.value && !stopRefresh) {
                updateCurrentPosition()
                val timeMillis: Long = (SettingsManager.barSpeed.value * 1000f).toLong()
                delay(timeMillis) // Wait one second to avoid refreshing all the time
            }

            if (playbackController.isEnded) {
                // It means the music has reached the end of playlist and the music is finished
                playbackController.currentPositionProgression.floatValue = 1f
            }

            isUpdatingPosition = false
        }
    }

    private fun updateCurrentPosition() {
        val playbackController: PlaybackController = PlaybackController.getInstance()
        if (playbackController.isEnded) {
            // It means the music has reached the end of playlist and the music is finished
            return
        }
        val maxPosition: Long = playbackController.musicPlaying.value!!.duration
        val newPosition: Long = playbackController.getCurrentPosition()

        playbackController.currentPositionProgression.floatValue =
            newPosition.toFloat() / maxPosition.toFloat()
    }
}