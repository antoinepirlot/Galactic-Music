# Releases (🇬🇧)
Tu peux retrouver ce fichier en [français 🇫🇷](RELEASES_FR.md)

## 1.1 (Android 5.1.1 Lollipop and later)

### 1.1.1

Improvements, Optimizations and Troubleshooting:

* When playing the next music when it is already the last one after shuffling.
* Playlists load faster at starting

### 1.1.0

Improvements, Optimizations and Troubleshooting:

* The size of the repeat one mode icon in the default playback settings displays correctly on
  different screen sizes
* Updating libraries

### 1.1.0-beta-1

New features:

* Added a new setting allowing the use of Audio Offload (sends playback to a dedicated process).
  This option allows you to optimize the battery
* Ability to add currently playing music to playlists via quick actions
* Navigation to albums, genres, artists, etc. from the menu with a long press on music

Improvements, Optimizations and Troubleshooting:

* Display issue when launching first music in Android Auto fixed
* The default random mode selection is represented by buttons with icons and more using a switch
* Displaying navigation bar button names

## 1.0 (Android 5.1.1 Lollipop and later)

### 1.0.3

Improvements, Optimizations and Troubleshooting:

* The album is no longer displayed in playback if the screen height size is too small in landscape
  mode
* Fixed crash when clicking the parameter button when audio permission is not granted
* Default language if system language is not supported is now English and no longer French
* Album titles will no longer exceed the width of the album
* Fixes to the text size of the "Music" section in the artist and genre views
* Adding music to several playlists is now resolved
* Fixed issue when adding 2 playlists with the same name with upper and lower case on different
  letters
* When adding music to a playlist and no playlist has been created, the application starts creating
  a new playlist

### 1.0.2

Improvements, Optimizations and Troubleshooting:

* Settings navigation works consistently for all menus.
* Design improvement

### 1.0.1

Improvements, Optimizations and Troubleshooting:

* Fix icons on playback views no more showing full black or full white when deactivated
* Harmonize shuffle and repeat icon by making the ones in settings with round border
* Adding a horizontal padding to Android Auto setting view
* Fix downloading release update

### 1.0.0

Satunes is now stable!

Improvements, Optimizations and Troubleshooting:

* Navigating to genres or albums with a name containing the '/' character can now work normally
* The design has been improved:
  * The shuffle and repeat buttons in the playback view (only in the phone app) are completely round
  * Support for screen sizes has been improved
* Added a menu in settings explaining how to activate Android Auto

### 1.0.0-preview-2

New features:

* Ability to change the name of a playlist

Improvements, Optimizations and Troubleshooting:

* Problems related to playback in Android Auto have been fixed:
  * No display at launch
  * Progress bar during repeat one mode that goes beyond the time limit
* Added text explaining why exporting and exporting playlists is in beta
* Attempted to fix crash when opening the app after a long time after pausing

### 1.0.0-preview-1

This version is planned to be deployed as a stable version if no bugs are detected during this
phase.

New features:

* Added a shuffle button to all Android Auto views
* Removed the "Musics" tab because there were too many elements in Android Auto.
* New settings:
  * Choice of repeat mode
  * Choice of shuffle mode
  * Ability to prevent other applications from pausing/playing playback

Improvements, Optimizations and Troubleshooting:

* Accents are sorted correctly
* Progress bar no longer glitches when enabling/disabling shuffle mode
* Background playback works correctly depending on the chosen setting
* Updates will take into account alpha, beta and preview 1,2,3, etc. for example "1.0.0-preview-1"
* Multi-language is now supported in Android Auto
* The shuffle button in Android Auto has an icon
* Edited link for github
* Improved design and support for different screen sizes.

## 0.10.0 (Android 5.1.1 Lollipop and later)

### 0.10.1-beta

Improvements, Optimizations and Troubleshooting:

* Songs are sorted alphabetically in folders, this avoids wrong playing order.
* Optimization of performance and battery when playing. The progress bar is refreshed only when the
  relevant view is open.

### 0.10.0-beta

The app is now available for Android Lollipop (5.1.1) and newer devices.
About 99.2% of you can install it 😜.

New features:

* It is possible to change the refresh rate of the progress bar
* It is possible to export and import playlists, in json format (future improvements will be
  developed.)
* It is possible to exclude ringtones

Improvements, Optimizations and Troubleshooting:

* The way permissions are managed has been improved and the application no longer crashes on first
  start
* The design has been improved:
  * In order to avoid blocking on small screens, all elements are scrollable while waiting to find a
    more suitable solution
  * Album image is displayed on screen in album page
  * The different views are scrollable in their entirety
  * The genre view displays, in addition to music, albums as in an artist's page
  * The artist name is displayed in the album view and allows redirection to it
  * The text areas have been harmonized and long texts will be cut if necessary
* Some light optimizations have been made

## 0.9 (Android 9 Pie and later)

### 0.9.2-beta

Improvements, Optimizations and Troubleshooting:

* Improved sorting
* Albums contain more than one song
* Taking into account the case where no data has been loaded.

### 0.9.1-beta

Improvements, Optimizations and Troubleshooting:

* Changed domain name to "io.github.antoinepirlot.satunes".
* The design has been reworked to bring default album art throughout the application.
* Album art displays in Android Auto.
* Database migrations can be done.
* Fixed some issues when initializing Android Auto in some cases.
* Screen recomposition when adding data such as playlists is now operational.

### 0.9.0-beta

MP3 Player finally has its own name "Satunes". This version becomes compatible with Android 9 Pie and newer.

New features:

* Clicking on the notification opens the application where the user left it.
* Compatibility with Android 9 Pie and newer
* Ability to add several songs to a playlist
* Ability to start shuffle and repeat a song in Android Auto

Improvements, Optimizations and Troubleshooting:

* The design has been improved.
* Parameters have been moved to subsections for better separation.
* Updates can be done regardless of the name of the application
* Toasts will no longer be displayed twice when updating.
* The album image is displayed in the album and music lists to make the lists more user-friendly.
* Optimization of battery usage
* Launching music from playlists is now fixed.
* The library which allows navigation has been updated and brings new animations.

## 0.8 (Android 11 Red Velvet Cake and later)

### 0.8.3-beta

Improvements, Optimizations and Troubleshooting:

* All albums that have the same name will be displayed on the screen.
* Artists, albums and genres without names will have an associated name.

### 0.8.2-beta

Improvements, Optimizations and Troubleshooting:

* Albums contain all their musics. An album is considered identical if its title and artist are the
  same.
* Now it is possible to download and install the update from the application.

### 0.8.1-beta

Improvements, Optimizations and Troubleshooting:

* When the user has added music to playlists but decides to delete them from the device, the
  application deletes missing music from playlists. This prevents the application from crashing
  every time you launch it.
* The update system has been redesigned. Now the user needs to click a button in the settings. This
  is no longer automatic in order to avoid unnecessary data consumption.
* Update checking has been fixed and now takes into account 4 types of updates:
  * Alpha: These are the most experimental and buggy versions. Features are not set in stone and may
    disappear with a subsequent update.
  * Beta: These are test versions to resolve problems with new features. This will be permanently
    added to the application (except in cases of major forces).
  * Preview: This is a release candidate which, if all goes well, will be deployed as a stable
    version.
  * Stable: This is a ready-to-use version. This is the recommended version for normal use. However, it may still present some bugs but these will be fixed
     rare and will not prevent the functionality from functioning properly.
* Adding music to several playlists is basically fixed.
* Crashes occurring when recomposing the UI following changes such as "Screen rotation" or "
  Activation of dark mode" will no longer cause the application to crash.
* The navigation has been corrected, now the user can return to other main screens from the settings
  following a recomposition of the screen as indicated in the previous point.
* When launching the application, it should no longer crash due to problems related to playback
  initialization.
* The data structure has been revised to resolve certain problems such as opening an album that was
  not the correct one or causing the application to crash when 2 albums have the same name.
* Optimization of data loading as well as the change of random mode (thanks to Kotlin coroutines).
* The progress bar has been collapsed on the sides to avoid conflict with gesture navigation.
* Playback in Android Auto has been fixed. This will result in a possible increase in battery usage
  but this has not yet been verified.

### 0.8.0-beta

New features:

* It is possible to check if an update is available from settings.
* Added new sections to the settings page.
* Added title to show where the user is located.
* Added a horizontal list displaying all artist's albums.
* Possibility to click on album (in the artist page or from the currently playing music) to display the album page.

Improvements, Optimizations and Troubleshooting:

* The setting that allows you to hide the file tab is repaired, the setting is saved correctly.
* The display of the settings page has been redesigned and improved.

## 0.7 (Android 11 Red Velvet Cake and later)

### 0.7.3-beta

Improvements, Optimization and Troubleshooting:

* Data loading has been optimized and is much faster.
* All text displayed in Android Auto is now escaped to see the correct characters.

### 0.7.2-beta

Improvements, Optimization and Troubleshooting:

* Navigation between pages is more intuitive
* Corrections made to playback
* Special characters are escaped, this fixes problems opening folder/media with a name containing a
  special character (for example: Hip-Hop/Rap)

### 0.7.1-beta

Now the app has its own icon 🤗 (note that I'm not an artist 🤭).

Problems resolutions:

* The button to create a playlist is now located at the bottom right of the screen.
* The playback has been overhauled and changing tracks should not cause any problems with the
  display of the music currently playing.
* A loading circle appears at launch when there is a lot of music to load to avoid a slower app
  launch.
* Music no longer automatically pauses in Android Auto when changing music from the queue list.

### 0.7.0-beta

Features:

* Add Playlist System

## 0.6 (Android 11 Red Velvet Cake and later)

### 0.6.0-beta

This version make the app compatible with Android 11 Red Velvet Cake and later.

## 0.5 (Android 14 Upside Down Cake and later)

### 0.5.1-beta

Fix issues:

* Optimisation of Android Auto
* Playback is correctly synchronized

### 0.5.0-beta

Features:

* Android Auto support
  * To activate Android Auto you can find instructions [here](README-EN.md)

Fix issues:

* Optimisation
* Show all music information on notification

## 0.4 (Android 14 Upside Down Cake and later)

### 0.4.2-beta

Fix issues:

* The media is paused when another media is playing

### 0.4.1-beta

Fix issues:

* Musics, Albums, etc. (Media) are sort by their title.

### 0.4.0-beta

Features:

* Show genres list and play music from them
* Settings
  * Allow users to stop the playback if the app is closed from multitasking
  * Allow users to pause playback if the headset is disconnected (cable or bluetooth)

Fix issues:

* Music from external storage can be played
* Show main storage as Root folder (e.g. "This Device" or "External Storage: C45-54")

## 0.3 (Android 14 Upside Down Cake and later)

### 0.3.2-beta

Fix issues:

* Disabling shuffle activation from outside the app (for example asking assistant to shuffle)

### 0.3.1-beta

Fix issues:

* Loading data on galaxy devices won't crash the app if not artwork found
* Disconnecting headset or bluetooth device will pause music
* The button play paused is now correctly synchronised with the state of the music

### 0.3.0-beta

New functionalities has landed. Now you can

* See album list and play music from albums
* Setting Page
    * Change bottom bar tabs by removing folders, artists or albums tabs

## 0.2 (Android 14 Upside Down Cake and later)

### 0.2.1-beta

In this version issue(s) have been fixed.

* When loading artwork fail, the app stop crashing

### 0.2.0-beta

In this version the album artwork is shown on the playback screen the music control bar has been
move
to the bottom of the screen.

## 0.1 (Android 14 Upside Down Cake and later)

### 0.1.0-beta

This version is the very first version of this app (in beta).
It includes basics functionalities of an mp3 player.
