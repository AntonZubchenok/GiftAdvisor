package com.zubchenok.giftadvisor.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class GiftProvider : ContentProvider() {
    val GIFTS = 100
    val GIFT_ID = 101
    val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(CONTENT_AUTHORITY, DB_NAME, GIFTS)
        uriMatcher.addURI(CONTENT_AUTHORITY, DB_NAME + "/#", GIFT_ID)
    }

    private lateinit var mDbHelper: GiftDbHelper

    override fun onCreate(): Boolean {
        mDbHelper = GiftDbHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor {
        val database = mDbHelper.readableDatabase
        val cursor: Cursor

        when (uriMatcher.match(uri)) {
            GIFTS -> cursor = database.query(TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder)

            GIFT_ID -> {
                val selection = "$_ID=?"
                cursor = database.query(TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder)
            }

            else -> throw IllegalArgumentException("Cannot query unknown URI: " + uri)
        }

        return cursor
    }

    override fun getType(uri: Uri): String? {
        val match = uriMatcher.match(uri)
        when (match) {
            GIFTS -> return CONTENT_LIST_TYPE
            GIFT_ID -> return CONTENT_ITEM_TYPE
            else -> throw IllegalStateException("Unknown URI $uri with match $match")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }


}