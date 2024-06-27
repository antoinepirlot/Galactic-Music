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

package io.github.antoinepirlot.satunes

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import io.github.antoinepirlot.satunes.database.models.relations.PlaylistWithMusics
import io.github.antoinepirlot.satunes.database.services.DataCleanerManager
import io.github.antoinepirlot.satunes.database.services.DatabaseManager
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.playback.services.PlaybackService
import io.github.antoinepirlot.utils.showToastOnUiThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.github.antoinepirlot.satunes.database.R as RDb

/**
 * @author Antoine Pirlot on 18/01/24
 */

internal class MainActivity : ComponentActivity() {

    companion object {
        internal lateinit var instance: MainActivity
        private const val IMPORT_PLAYLIST_CODE = 1
        private const val EXPORT_PLAYLIST_CODE = 2
        internal var playlistsToExport: Array<PlaylistWithMusics> = arrayOf()
        private val DEFAULT_URI =
            Uri.parse(Environment.getExternalStorageDirectory().path + '/' + Environment.DIRECTORY_DOCUMENTS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        instance = this
        setNotificationOnClick()
        SettingsManager.loadSettings(context = this@MainActivity)
        super.onCreate(savedInstanceState)
        setContent {
            Satunes()
        }
        DataCleanerManager.removeApkFiles(context = baseContext)
    }

    internal fun isAudioAllowed(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                instance,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                instance,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * When the user click on playing notification, the app is opened.
     */
    @OptIn(UnstableApi::class)
    private fun setNotificationOnClick() {
        val intent: Intent = this.intent
        CoroutineScope(Dispatchers.IO).launch {
            while (PlaybackService.mediaSession == null) {
                // The init has to be completed
            }
            val pendingIntent = PendingIntent.getActivity(
                baseContext.applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            PlaybackService.mediaSession!!.setSessionActivity(pendingIntent)
        }
    }

    //TODO find a way to move it to database module, no solution found instead of here
    // No enough knowledge about Activity
    fun createFileToExportPlaylists(defaultFileName: String) {
        if (playlistsToExport.isEmpty()) {
            showToastOnUiThread(
                context = this,
                message = this.getString(RDb.string.no_playlist)
            )
            return
        }
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, defaultFileName)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, DEFAULT_URI)
            }
        }
        startActivityForResult(intent, EXPORT_PLAYLIST_CODE)
    }

    fun openFileToImportPlaylists() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, DEFAULT_URI)
            }
        }
        startActivityForResult(intent, IMPORT_PLAYLIST_CODE)
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == EXPORT_PLAYLIST_CODE) {
                data?.data?.also {
                    if (it.path == null) {
                        showToastOnUiThread(
                            context = this,
                            message = this.getString(R.string.no_file_created)
                        )
                    }
                    DatabaseManager(context = this)
                        .exportPlaylists(
                            context = this,
                            playlistWithMusics = playlistsToExport,
                            uri = it
                        )
                    playlistsToExport = arrayOf()
                }
            } else if (requestCode == IMPORT_PLAYLIST_CODE) {
                data?.data?.also {
                    DatabaseManager(context = this).importPlaylists(context = this, uri = it)
                }
            }
        }
    }
}