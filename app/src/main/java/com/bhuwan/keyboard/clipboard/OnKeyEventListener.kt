// SPDX-License-Identifier: GPL-3.0-only

package com.bhuwan.argonboard.keyboard.clipboard

interface OnKeyEventListener {

    fun onKeyDown(clipId: Long)

    fun onKeyUp(clipId: Long)

}