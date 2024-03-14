package services

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog

class DialogService() {

    private lateinit var builder: AlertDialog.Builder

    companion object Factory{
        fun create(context: Context): DialogService {
            val x = DialogService()
            x.setService(context)
            return x
        }
    }

    private fun setService(context: Context){
        builder = AlertDialog.Builder(context)
    }

    fun getDialog(title: String, message: String, r: Runnable): AlertDialog{
        builder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Да"){
                    dialog, which ->
                    Handler(Looper.getMainLooper()).post(r)
            }
            .setNegativeButton("Нет"){
                    dialog, which ->

            }
        return builder.create()
    }
}