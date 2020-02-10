package com.kay.samplearch.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.kay.samplearch.R
import com.kay.samplearch.common.extensions.getColorCompat
import kotlin.reflect.KClass

class AlertDialogFragment : BaseDialogFragment() {
    override val layoutId = 0

    override fun provideViewModel(): Map<KClass<*>, () -> BaseViewModel> = mapOf()

    var message: String = ""
    var title: String = ""
    var positive: String = ""
    var negative: String? = null
    var negativeCallback: (DialogInterface) -> Unit = { it.dismiss() }
    var positiveCallback: (DialogInterface) -> Unit = { it.dismiss() }
    var buttonColor: Int = R.color.ui_color_red

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb = AlertDialog.Builder(context!!)
            .setTitle(title)
            .setPositiveButton(positive) { dialog, which ->
                positiveCallback(dialog)
            }

        if (message.isNotEmpty()){
            adb.setMessage(message)
        }

        if (negative != null) {
            adb.setNegativeButton(negative) { dialog, which ->
                negativeCallback(dialog)
            }
        }

        return adb.create()
    }

    override fun onStart() {
        super.onStart()
        val alertDialog = (dialog as AlertDialog)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context!!.getColorCompat(buttonColor))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context!!.getColorCompat(buttonColor))
    }

    override fun handleActivityBackPressed(): Boolean {
        negativeCallback(dialog as DialogInterface)
        return true
    }
}