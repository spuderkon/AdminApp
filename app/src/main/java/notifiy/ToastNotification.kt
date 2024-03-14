package notifiy

import android.content.Context
import android.widget.Toast

object ToastNotification {

    fun showLongMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}