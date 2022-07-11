package eu.app.editedvideosplayer.ui.common

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns


fun Uri.getFileName(context: Context): String {
    if (this.scheme == "content") {
        context.contentResolver.query(this, null, null, null, null)?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    return this.path?.let { it.substring(it.lastIndexOf('/') + 1) } ?: "Unknown file name"
}