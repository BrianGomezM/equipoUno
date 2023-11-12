package com.univalle.picobotellamvvm.view.dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.univalle.picobotellamvvm.R
class DeleteDialog {
    companion object {
        fun showDialog(context: Context, id: Int, mensaje: String): AlertDialog {
            val alertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.DialogStyle))
            alertDialog.setCancelable(false)
            val title = TextView(context)
            title.text = context.getString(R.string.tituloDD)
            title.gravity = Gravity.CENTER
            title.textSize = 20f
            title.setTextColor(Color.BLACK)
            title.setTypeface(null, Typeface.BOLD)
            title.setPadding(0, 20, 0, 20)
            alertDialog.setCustomTitle(title)

            alertDialog.setMessage(mensaje)
            alertDialog.setPositiveButton(context.getString(R.string.respuestaP)) { dialog, _ ->
                Toast.makeText(context, (context.getString(R.string.respuestaP2)), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton(context.getString(R.string.respuestaN)) { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog2 = alertDialog.create()
            alertDialog2.setOnShowListener {
                val colorAccent = context.resources.getColor(R.color.colorAccent)
                alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(colorAccent)
                alertDialog2.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(colorAccent)
            }
            return alertDialog2
        }
    }
}
