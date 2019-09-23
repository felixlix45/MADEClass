package com.felix.madeclass.provider

import android.net.Uri
import com.felix.madeclass.model.MovieFavorite

class DatabaseContract {
    companion object {
        const val AUTHORITY: String = "com.felix.madeclass.appcontentprovider.provider"
        val URI_MOVIE = Uri.parse("context://${AppContentProvider.AUTHORITY}/${MovieFavorite.TABLE_NAME}")
    }
}