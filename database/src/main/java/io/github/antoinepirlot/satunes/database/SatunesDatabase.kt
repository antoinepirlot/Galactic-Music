/*
 * This file is part of Satunes.
 *
 * Satunes is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Satunes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Satunes.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * **** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/Satunes
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package io.github.antoinepirlot.satunes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.antoinepirlot.satunes.database.daos.MusicDAO
import io.github.antoinepirlot.satunes.database.daos.MusicsPlaylistsRelDAO
import io.github.antoinepirlot.satunes.database.daos.PlaylistDAO
import io.github.antoinepirlot.satunes.database.models.tables.MusicDB
import io.github.antoinepirlot.satunes.database.models.tables.MusicsPlaylistsRel
import io.github.antoinepirlot.satunes.database.models.tables.Playlist

/**
 * @author Antoine Pirlot on 27/03/2024
 */

@Database(
    entities = [
        MusicDB::class,
        MusicsPlaylistsRel::class,
        Playlist::class
    ],
    version = 1,
    exportSchema = true,
)
internal abstract class SatunesDatabase : RoomDatabase() {
    companion object {
        private var database: SatunesDatabase? = null

        fun getDatabase(context: Context): SatunesDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context = context,
                    SatunesDatabase::class.java,
                    "Satunes-database"
                ).build()
            }
            return database!!
        }
    }

    abstract fun musicDao(): MusicDAO
    abstract fun playlistDao(): PlaylistDAO
    abstract fun musicsPlaylistsRelDao(): MusicsPlaylistsRelDAO
}