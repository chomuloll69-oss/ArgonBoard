// SPDX-License-Identifier: Apache-2.0 AND GPL-3.0-only
package com.bhuwan.argonboard.latin

import android.app.Application
import android.os.Build
import com.bhuwan.argonboard.keyboard.emoji.SupportedEmojis
import com.bhuwan.argonboard.latin.define.DebugFlags
import com.bhuwan.argonboard.latin.settings.Defaults
import com.bhuwan.argonboard.latin.settings.Settings
import com.bhuwan.argonboard.latin.utils.FoldableUtils
import com.bhuwan.argonboard.latin.utils.LayoutUtilsCustom
import com.bhuwan.argonboard.latin.utils.Log
import com.bhuwan.argonboard.latin.utils.SubtypeSettings
import com.bhuwan.argonboard.latin.utils.prefs
import com.bhuwan.argonboard.latin.utils.upgradeToolbarPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DebugFlags.init(this)
        FoldableUtils.init(this)
        Settings.init(this)
        SubtypeSettings.init(this)

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch { // do some uncritical work in background for faster startup
            SupportedEmojis.load(this@App)
            LayoutUtilsCustom.removeMissingLayouts(this@App)
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            @Suppress("DEPRECATION")
            Log.i(
                "startup", "Starting ${applicationInfo.processName} version ${packageInfo.versionName} (${
                    packageInfo.versionCode
                }) on Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})"
            )
        }

        RichInputMethodManager.init(this)
        checkVersionUpgrade(this)
        if (BuildConfig.DEBUG) // do this on every debug apk start because we may work on adding a new toolbar key
            upgradeToolbarPrefs(prefs())
        transferOldPinnedClips(this) // todo: remove in a few months, maybe end 2026
        app = this
        Defaults.initDynamicDefaults(this)
    }

    companion object {
        // used so JniUtils can access application once
        private var app: App? = null
        fun getApp(): App? {
            val application = app
            app = null
            return application
        }
    }
}
