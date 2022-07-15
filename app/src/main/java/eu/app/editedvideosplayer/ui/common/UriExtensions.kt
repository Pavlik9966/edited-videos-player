package eu.app.editedvideosplayer.ui.common

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
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

fun Uri.getPath(context: Context): String? {

    when {
        DocumentsContract.isDocumentUri(context, this) -> {
            when {
                isExternalStorageDocument(this) -> {
                    val docId = DocumentsContract.getDocumentId(this)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                }
                isDownloadsDocument(this) -> {
                    val id = DocumentsContract.getDocumentId(this)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        id.toLong()
                    )
                    return getDataColumn(context, contentUri, null, null)
                }
                isMediaDocument(this) -> {
                    val docId = DocumentsContract.getDocumentId(this)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }
        }
        "content".equals(this.scheme, ignoreCase = true) -> {
            return getDataColumn(context, this, null, null)
        }
        "file".equals(this.scheme, ignoreCase = true) -> {
            return this.path
        }
    }
    return null
}

private fun getDataColumn(
    context: Context,
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String? {

    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return null
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun getUriFromPath(path: String?): Uri = Uri.parse("file://$path")