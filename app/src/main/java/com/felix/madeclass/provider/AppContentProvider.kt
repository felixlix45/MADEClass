package com.felix.madeclass.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.MovieFavorite


class AppContentProvider : ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    companion object {
        const val AUTHORITY: String = "com.felix.madeclass.appcontentprovider.provider"
    }

    init {
        uriMatcher.addURI(AUTHORITY, "movie_table", 1)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val code = uriMatcher.match(uri)
        if (code == 1) {
            val mContext: Context = context ?: return null
            val movieDao: MovieDao = FavoriteDatabase.getInstance(mContext)!!.movieDao()
            val cursor: Cursor
            cursor = movieDao.getAllMovie3()
            cursor.setNotificationUri(mContext.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            1 -> {
                return "vnd.android.cursor.dir/$AUTHORITY.${MovieFavorite.TABLE_NAME}"
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }
}