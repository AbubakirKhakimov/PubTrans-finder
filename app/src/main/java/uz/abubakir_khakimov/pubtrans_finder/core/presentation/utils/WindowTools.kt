package uz.abubakir_khakimov.pubtrans_finder.core.presentation.utils

import android.os.Build
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.WindowInsetsController
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object WindowTools {

    fun setDecorFitsSystemWindows(
        window: Window,
        decorFitsSystemWindows: Boolean
    ) = if (Build.VERSION.SDK_INT >= 30) setDecorFitsSystemWindowsNew(
        window = window,
        decorFitsSystemWindows = decorFitsSystemWindows
    ) else setDecorFitsSystemWindowsOld(
        window = window,
        decorFitsSystemWindows = decorFitsSystemWindows
    )

    //Build SDK >= 30
    private fun setDecorFitsSystemWindowsNew(
        window: Window,
        decorFitsSystemWindows: Boolean
    ) = WindowCompat.setDecorFitsSystemWindows(
        /* window = */ window,
        /* decorFitsSystemWindows = */ decorFitsSystemWindows
    )

    //Build SDK >= 16
    private fun setDecorFitsSystemWindowsOld(
        window: Window,
        decorFitsSystemWindows: Boolean
    ) {
        val decorFitsFlags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        val decorView = window.decorView
        val sysUiVis = decorView.systemUiVisibility
        decorView.systemUiVisibility = if (decorFitsSystemWindows) sysUiVis and decorFitsFlags.inv()
        else sysUiVis or decorFitsFlags
    }

    fun adjustNavigationBar(rootView: View) = if (Build.VERSION.SDK_INT >= 30)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsets ->

            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.

            rootView.layoutParams = (rootView.layoutParams as FrameLayout.LayoutParams).apply {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    else Unit

    fun setSystemUiLightStatusBar(window: Window, isLightStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val systemUiAppearance = if (isLightStatusBar) {
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            } else {
                0
            }
            window.insetsController?.setSystemBarsAppearance(systemUiAppearance,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else {
            val systemUiVisibilityFlags = if (isLightStatusBar) {
                window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window.decorView.systemUiVisibility = systemUiVisibilityFlags
        }
    }
}