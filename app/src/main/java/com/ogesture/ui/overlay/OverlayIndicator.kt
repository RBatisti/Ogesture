package com.ogesture.ui.overlay

import android.graphics.Rect

/** A cosmetic gesture indicator living in its own non-touchable overlay window. */
interface OverlayIndicator {
    fun attach()
    fun detach()

    /**
     * Hide (alpha 0) or restore the indicator window. Used while an excluded app is in
     * front, and while a touch whose point lies under this window is being replayed: a
     * non-touchable overlay window counts as 0.8 "obscuring opacity", and two of ours
     * stacked (zone + indicator) exceed Android's 0.8 limit, so the system would silently
     * drop the replayed touch as untrusted. Windows with alpha 0 are exempt.
     */
    fun setWindowHidden(hidden: Boolean)

    /**
     * Permanently hide (alpha 0) the indicator per the user's own preference — independent
     * of [setWindowHidden]'s transient reasons. The gesture zone underneath keeps working
     * exactly as before; only the visible mark is removed. The final alpha is 0 whenever
     * either this or [setWindowHidden] asks for it.
     */
    fun setUserHidden(hidden: Boolean)

    /**
     * Screen-space bounds of the indicator's window while it is attached and not hidden,
     * else null. Used to decide whether a replay's tap point needs this window hidden
     * (and the injection delayed) before the system will trust the injected touch.
     */
    fun windowBounds(): Rect?
}