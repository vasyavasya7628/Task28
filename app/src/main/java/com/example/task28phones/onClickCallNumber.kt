package com.example.task28phones

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

interface OnClickCallNumber {
    companion object{
        fun makeCall(context: Context, mob: String) {
            try {
                val intent = Intent(Intent.ACTION_DIAL)

                intent.data = Uri.parse("tel:$mob")
                context.startActivity(intent)
            } catch (e: java.lang.Exception) {
                Toast.makeText(
                    context,
                    e.toString(), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}