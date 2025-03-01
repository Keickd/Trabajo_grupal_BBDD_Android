package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

object ImageHelper {

    fun saveImageToInternalStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        directoryName: String
    ): Uri? {
        return try {
            val directory = File(context.filesDir, directoryName) //team_images
            if (!directory.exists()) directory.mkdirs()

            val file = File(directory, "$fileName.jpg")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getAccessibleUri(context: Context, uri: Uri): Uri? {
        val cursor = context.contentResolver.query(
            uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val filePath = it.getString(columnIndex)
                return Uri.fromFile(File(filePath))
            }
        }
        return null
    }

}