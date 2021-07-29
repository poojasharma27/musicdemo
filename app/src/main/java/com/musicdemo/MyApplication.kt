package com.musicdemo

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    private var singleton: MyApplication? = null


    fun getInstance(): MyApplication? {
        return singleton
    }


    override fun onCreate() {
        super.onCreate()
        singleton = this
        mContext = applicationContext
    }
  companion object {
       var mContext: Context? = null
      /*fun getAppContext(): Context? {
          return mContext
      }*/
  }
    override fun onLowMemory() {
        super.onLowMemory()
    }



}