package com.zubchenok.giftadvisor.data

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class GiftDbHelper(context: Context) :
        SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "gifts.db"
        val DATABASE_VERSION = 1
    }
}