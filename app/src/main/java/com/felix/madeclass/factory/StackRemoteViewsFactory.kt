package com.felix.madeclass.factory

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.felix.madeclass.FavoriteWidget
import com.felix.madeclass.R


class StackRemoteViewsFactory(context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var mContext: Context = context

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.heart_48))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.clock_64))
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.ivWidget, mWidgetItems.get(position))

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