package com.felix.madeclass.factory

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.felix.madeclass.FavoriteWidget
import com.felix.madeclass.R
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.repository.MovieRepository
import com.felix.madeclass.viewmodel.FavoriteMoviesViewModel


class StackRemoteViewsFactory(context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var mContext: Context = context
    private lateinit var newItems: List<MovieFavorite>
    private lateinit var cursor: Cursor

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        val identityToken: Long = Binder.clearCallingIdentity()

        val instance = Room.databaseBuilder(mContext, FavoriteDatabase::class.java, "favorite_database").allowMainThreadQueries().build()

        val cursor = instance.movieDao().getAllMovie3()
//        Log.d("FACTORY", cursor.getString(4))
        val newItem: List<MovieFavorite> = instance.movieDao().getAllMovie2()
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