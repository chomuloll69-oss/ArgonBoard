// SPDX-License-Identifier: GPL-3.0-only

package com.bhuwan.argonboard.latin.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

@RequiresApi(Build.VERSION_CODES.S)
object FrostedGlass {

    fun create(
        context: Context,
        isNight: Boolean,
        alpha: Int = 42
    ): Drawable {
        val base = ContextCompat.getColor(
            context,
            if (isNight) android.R.color.system_neutral1_900 else android.R.color.system_neutral1_50
        )
        val accentTint = ContextCompat.getColor(
            context,
            if (isNight) android.R.color.system_accent1_300 else android.R.color.system_accent1_400
        )

        val glassLayer = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(ColorUtils.setAlphaComponent(base, alpha))
        }

        val sheenLayer = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                ColorUtils.setAlphaComponent(accentTint, if (isNight) 14 else 18),
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        ).apply {
            shape = GradientDrawable.RECTANGLE
        }

        return LayerDrawable(arrayOf(glassLayer, sheenLayer))
    }

    fun isSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
