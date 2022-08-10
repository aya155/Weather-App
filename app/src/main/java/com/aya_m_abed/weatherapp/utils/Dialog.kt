package com.aya_m_abed.weatherapp.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

fun setProgressDialog(context: Context, message:String): AlertDialog {
    val dialogLinearLayoutPadding = 30
    val dialogLinearLayout = LinearLayout(context)
    dialogLinearLayout.orientation = LinearLayout.HORIZONTAL
    dialogLinearLayout.setPadding(dialogLinearLayoutPadding, dialogLinearLayoutPadding, dialogLinearLayoutPadding, dialogLinearLayoutPadding)
    dialogLinearLayout.gravity = Gravity.CENTER
    var dialogLinearLayoutParam = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    dialogLinearLayoutParam.gravity = Gravity.CENTER
    dialogLinearLayout.layoutParams = dialogLinearLayoutParam

    val progressBar = ProgressBar(context)
    progressBar.isIndeterminate = true
    progressBar.setPadding(0, 0, dialogLinearLayoutPadding, 0)
    progressBar.layoutParams = dialogLinearLayoutParam

    dialogLinearLayoutParam = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    dialogLinearLayoutParam.gravity = Gravity.CENTER
    val tvText = TextView(context)
    tvText.text = message
    tvText.textSize = 20.toFloat()
    tvText.layoutParams = dialogLinearLayoutParam

    dialogLinearLayout.addView(progressBar)
    dialogLinearLayout.addView(tvText)

    val builder = AlertDialog.Builder(context)
    builder.setCancelable(true)
    builder.setView(dialogLinearLayout)

    val dialog = builder.create()
    val window = dialog.window
    if (window != null) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
    }
    return dialog
}