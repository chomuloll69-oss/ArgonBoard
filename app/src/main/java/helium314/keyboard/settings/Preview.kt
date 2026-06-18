// SPDX-License-Identifier: GPL-3.0-only
package com.bhuwan.argonboard.settings

import android.content.Context
import com.bhuwan.argonboard.keyboard.internal.KeyboardIconsSet
import com.bhuwan.argonboard.latin.settings.Settings
import com.bhuwan.argonboard.latin.utils.SubtypeSettings

// file is meant for making compose previews work

fun initPreview(context: Context) {
    Settings.init(context)
    SubtypeSettings.init(context)
    Settings.getInstance().loadSettings(context)
    SettingsActivity.settingsContainer = SettingsContainer(context)
    KeyboardIconsSet.instance.loadIcons(context)
}
