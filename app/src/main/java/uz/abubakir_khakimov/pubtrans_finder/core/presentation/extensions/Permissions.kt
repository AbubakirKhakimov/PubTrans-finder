package uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.hasPermissions(vararg permissions: String): Boolean = permissions
    .all { hasPermission(permission = it) }

fun Context.hasPermissions(vararg permissions: String): Boolean = permissions
    .all { hasPermission(permission = it) }

fun Fragment.hasPermission(permission: String): Boolean =
    requireActivity().hasPermission(permission = permission)

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        /* context = */ this,
        /* permission = */ permission
    ) == PackageManager.PERMISSION_GRANTED