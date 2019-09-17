package com.felix.madeclass.services

import android.content.Intent
import android.widget.RemoteViewsService
import com.felix.madeclass.factory.StackRemoteViewsFactory

class StackWidgetServices : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}