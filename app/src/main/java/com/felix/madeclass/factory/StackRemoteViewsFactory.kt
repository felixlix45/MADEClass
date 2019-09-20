package com.felix.madeclass.factory

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.Target
import com.felix.madeclass.FavoriteWidget
import com.felix.madeclass.R
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.MovieFavorite


class StackRemoteViewsFactory(context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var mContext: Context = context
    private lateinit var cursor: Cursor
    private lateinit var newItem: List<MovieFavorite>
    private lateinit var instance: FavoriteDatabase

    override fun onCreate() {
        newItem = ArrayList()
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {

        if (newItem.isNotEmpty()) {
            newItem = ArrayList()
        }

        val identityToken: Long = Binder.clearCallingIdentity()

        instance = Room.databaseBuilder(mContext, FavoriteDatabase::class.java, "favorite_database").allowMainThreadQueries().build()
        val cursor = instance.movieDao().getAllMovie3()

        newItem = instance.movieDao().getAllMovie2()

        Log.d("Factory", newItem.size.toString())
        for (item in newItem) {
            val bitmap: Bitmap = Glide.with(mContext.applicationContext)
                    .asBitmap()
                    .priority(Priority.IMMEDIATE)
                    .load(item.photoBackdropHigh)
                    .into(0, Target.SIZE_ORIGINAL)
                    .get()
            mWidgetItems.add(Bitmap.createBitmap(bitmap))
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.ivWidget, mWidgetItems[position])

        val extras = Bundle()
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.ivWidget, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}