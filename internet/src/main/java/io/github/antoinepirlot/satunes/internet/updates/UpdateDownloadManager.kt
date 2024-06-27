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

package io.github.antoinepirlot.satunes.internet.updates

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.getSystemService
import io.github.antoinepirlot.satunes.internet.R
import io.github.antoinepirlot.satunes.internet.updates.Versions.ALPHA
import io.github.antoinepirlot.satunes.internet.updates.Versions.ALPHA_APK_REGEX
import io.github.antoinepirlot.satunes.internet.updates.Versions.BETA
import io.github.antoinepirlot.satunes.internet.updates.Versions.BETA_APK_REGEX
import io.github.antoinepirlot.satunes.internet.updates.Versions.PREVIEW
import io.github.antoinepirlot.satunes.internet.updates.Versions.PREVIEW_APK_REGEX
import io.github.antoinepirlot.satunes.internet.updates.Versions.RELEASES_URL
import io.github.antoinepirlot.satunes.internet.updates.Versions.RELEASE_APK_REGEX
import io.github.antoinepirlot.satunes.internet.updates.Versions.versionType
import io.github.antoinepirlot.utils.showToastOnUiThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response

/**
 * @author Antoine Pirlot on 14/04/2024
 */
object UpdateDownloadManager {
    private var downloadId: Long = -1
    private const val MIME_TYPE = "application/vnd.android.package-archive"

    fun downloadUpdateApk(context: Context) {
        if (UpdateCheckManager.downloadStatus.value == APKDownloadStatus.CHECKING || UpdateCheckManager.downloadStatus.value == APKDownloadStatus.DOWNLOADING) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            UpdateCheckManager.downloadStatus.value = APKDownloadStatus.CHECKING
            showToastOnUiThread(
                context = context,
                message = context.getString(R.string.download_checking)
            )
            try {
                if (UpdateCheckManager.updateAvailableStatus.value != UpdateAvailableStatus.AVAILABLE) {
                    //Can't be downloaded
                    UpdateCheckManager.downloadStatus.value = APKDownloadStatus.NOT_FOUND
                    showToastOnUiThread(
                        context = context,
                        message = context.getString(R.string.download_not_found)
                    )
                    return@launch
                }
                val downloadUrl: String =
                    getDownloadUrl(context = context) ?: return@launch
                val appName: String = downloadUrl.split("/").last()
                val downloadManager: DownloadManager = context.getSystemService()!!
                val downloadUri: Uri = Uri.parse(downloadUrl)
                val req: DownloadManager.Request = DownloadManager.Request(downloadUri)
                req.setMimeType(MIME_TYPE)
                val destination =
                    "file://" + context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        .toString() + "/$appName"
                val destinationUri: Uri = Uri.parse(destination)
                req.setDestinationUri(destinationUri)

                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                setDownloadReceiver(context = context)
                downloadId = downloadManager.enqueue(req)
                UpdateCheckManager.downloadStatus.value = APKDownloadStatus.DOWNLOADING
                showToastOnUiThread(
                    context = context,
                    message = context.getString(R.string.downloading)
                )
            } catch (_: Exception) {
                UpdateCheckManager.downloadStatus.value = APKDownloadStatus.FAILED
                showToastOnUiThread(
                    context = context,
                    message = context.getString(R.string.download_failed)
                )
                UpdateCheckManager.updateAvailableStatus.value = UpdateAvailableStatus.UNDEFINED
            }
        }
    }

    /**
     * Get the download url for the latest version. It looks like:
     * "https://github.com/antoinepirlot/Satunes/releases/download/vx.y.z-beta/[app name]_vx.y.z[-versionType].apk"
     *
     * @return the download url or null if not found
     */
    private fun getDownloadUrl(context: Context): String? {
        val regex: Regex =
            when (versionType) {
                ALPHA -> ALPHA_APK_REGEX
                BETA -> BETA_APK_REGEX
                PREVIEW -> PREVIEW_APK_REGEX
                else -> RELEASE_APK_REGEX
            }
        val res: Response = UpdateCheckManager.getUrlResponse(
            context = context,
            url = "${RELEASES_URL}/expanded_assets/${UpdateCheckManager.latestVersion.value}"
        )!!
        if (!res.isSuccessful) {
            res.close()
            UpdateCheckManager.downloadStatus.value = APKDownloadStatus.NOT_FOUND
            showToastOnUiThread(
                context = context,
                message = context.getString(R.string.download_not_found)
            )
            return null
        }
        val page: String = res.body!!.string()
        res.close()
        var apkFileName: String? = regex.find(
            page,
            0
        )?.value
        if (apkFileName == null) {
            UpdateCheckManager.downloadStatus.value = APKDownloadStatus.NOT_FOUND
            showToastOnUiThread(
                context = context,
                message = context.getString(R.string.download_not_found)
            )
            return null
        }

        apkFileName = apkFileName.drop(1).dropLast(1) //Avoid > and <
        return "${RELEASES_URL}/download/${UpdateCheckManager.latestVersion.value}/$apkFileName"
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun setDownloadReceiver(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                DownloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                Context.RECEIVER_EXPORTED
            )
        } else
            context.registerReceiver(
                DownloadReceiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
    }

    /**
     * Launch the installation procedure by request the user to install the app.
     */
    fun installUpdate(context: Context) {
        val downloadManager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val contentUri: Uri = downloadManager.getUriForDownloadedFile(downloadId)
        val install = Intent(Intent.ACTION_VIEW)
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        install.data = contentUri
        context.startActivity(install)
    }
}