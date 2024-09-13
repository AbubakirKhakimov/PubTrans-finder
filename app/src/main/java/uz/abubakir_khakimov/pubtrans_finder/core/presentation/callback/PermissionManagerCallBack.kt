package uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback

interface PermissionManagerCallBack {

    fun onActivityResult(result: Map<String, Boolean>, tag: Any)
}