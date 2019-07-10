package com.felix.madeclass

import android.content.Context

class ResourceProvider {
    private lateinit var  mContext: Context

    fun ResourceProvider(mContext: Context) {
        this.mContext = mContext
    }

    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }
}