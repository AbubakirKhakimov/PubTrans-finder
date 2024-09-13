package uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback.PermissionManagerCallBack

interface PermissionManager {

    fun registerActivityResult(activity: AppCompatActivity, callBack: PermissionManagerCallBack? = null)

    fun checkPermissions(
        vararg permissions: String,
        autoAsk: Boolean = false,
        tag: Any = Unit
    ): Boolean

    fun askPermissions(vararg permissions: String, tag: Any = Unit)
}