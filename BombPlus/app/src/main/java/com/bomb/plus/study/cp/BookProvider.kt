package com.bomb.plus.study.cp

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.bomb.common.net.log

class BookProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        log("cp---insert--${Thread.currentThread().name}")
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        log("cp---query--${Thread.currentThread().name}")
        return null
    }

    override fun onCreate(): Boolean {
        log("cp---onCreate--${Thread.currentThread().name}")
        return false
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        log("cp---update--${Thread.currentThread().name}")
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        log("cp---delete--${Thread.currentThread().name}")
        return 0
    }

    override fun getType(uri: Uri): String? {
        return "*/*"
    }
}