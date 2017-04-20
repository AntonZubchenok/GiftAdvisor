package com.zubchenok.giftadvisor.data

import android.content.ContentResolver
import android.net.Uri

//Database constants
val CONTENT_AUTHORITY = "com.zubchenok.giftadvisor.data.gifts"
val DB_URI: Uri = Uri.parse("content://" + CONTENT_AUTHORITY)
val DB_NAME = "gifts"
val DB_PATH = "/data/data/com.zubchenok.giftadvisor/databases/"


//Table constants
val TABLE_URI = Uri.withAppendedPath(DB_URI, DB_NAME)
val TABLE_NAME = "gifts"

val _ID = "_id"                                            //INTEGER
val COLUMN_NAME = "name"                                   //TEXT
val COLUMN_SEX = "sex"                                     //INTEGER
val COLUMN_AGE_MIN = "age_min"                             //INTEGER
val COLUMN_AGE_MAX = "age_max"                             //INTEGER
val COLUMN_PRICE_MIN = "price_min"                         //INTEGER
val COLUMN_PRICE_MAX = "price_max"                         //INTEGER
val COLUMN_IMAGE = "image"                                 //TEXT
val COLUMN_REASON_ANY = "reason_any"                       //INTEGER
val COLUMN_REASON_BIRTHDAY = "reason_birthday"             //INTEGER
val COLUMN_REASON_NEW_YEAR = "reason_new_year"             //INTEGER
val COLUMN_REASON_WEDDING = "reason_wedding"               //INTEGER
val COLUMN_REASON_8_MAR = "reason_8_mar"                   //INTEGER
val COLUMN_REASON_23_FEB = "reason_23_feb"                 //INTEGER
val COLUMN_REASON_VALENTINES_DAY = "reason_valentines_day" //INTEGER

val CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
        CONTENT_AUTHORITY + "/" + DB_NAME
val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
        CONTENT_AUTHORITY + "/" + DB_NAME

//Sex constants
val SEX_ANY = -1
val SEX_MALE = 1
val SEX_FEMALE = 0

