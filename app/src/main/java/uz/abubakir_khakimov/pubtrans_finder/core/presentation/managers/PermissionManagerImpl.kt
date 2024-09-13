package uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback.PermissionManagerCallBack
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.hasPermissions

class PermissionManagerImpl: PermissionManager, ActivityResultCallback<Map<String, Boolean>> {

    private var activityResultLauncher: ActivityResultLauncher<Array<String>>? = null
    private var activity: AppCompatActivity? = null
    private var callBack: PermissionManagerCallBack? = null
    private var tag: Any = Unit

    override fun onActivityResult(result: Map<String, Boolean>) {
        callBack?.onActivityResult(result = result, tag = tag)
    }

    override fun registerActivityResult(
        activity: AppCompatActivity,
        callBack: PermissionManagerCallBack?
    ) {
        this.activity = activity
        this.callBack = callBack

        activityResultLauncher = activity.registerForActivityResult(
            /* contract = */ ActivityResultContracts.RequestMultiplePermissions(),
            /* callback = */ this
        )
    }

    override fun checkPermissions(
        vararg permissions: String,
        autoAsk: Boolean,
        tag: Any
    ): Boolean = activity?.hasPermissions(permissions = permissions)?.let {
        if (autoAsk) {
            if (it) callBack?.onActivityResult(
                result = permissions.associateWith { true },
                tag = tag
            ) else askPermissions(permissions = permissions, tag = tag)
        }

        it
    } ?: throw IllegalStateException(ACTIVITY_NOT_FOUND_MESSAGE)

    override fun askPermissions(vararg permissions: String, tag: Any) {
        this.tag = tag

        activityResultLauncher?.launch(/* input = */ arrayOf(elements = permissions))
            ?: throw IllegalStateException(ACTIVITY_NOT_FOUND_MESSAGE)
    }

    companion object{

        private const val ACTIVITY_NOT_FOUND_MESSAGE = "Activity not found! Please first call the " +
                "registerActivityResult() function in the onCreate{} or onAttach() activity block."
    }
}