package com.zubchenok.giftadvisor.activity

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zubchenok.giftadvisor.COMMA
import com.zubchenok.giftadvisor.R
import com.zubchenok.giftadvisor.data.*
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        //Get gift id from Intent and put it into Bundle which will be sent to Loader
        val giftId = intent.getIntExtra(_ID, -1)
        val bundle = Bundle(1).apply {
            putInt(_ID, giftId)
        }

        //Start Loader which will get from database data about selected gift and will show it
        loaderManager.restartLoader<Cursor>(0, bundle, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {

        //Get data about selected gift from database
        val selection = "($_ID=?)"
        val selectionArgs = arrayOf(args.getInt(_ID).toString())
        return CursorLoader(this, TABLE_URI, null, selection, selectionArgs, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        data.moveToFirst()

        //Show gift image
        val imageName = with(data) { getString(getColumnIndex(COLUMN_IMAGE)) }
        val imageId = resources.getIdentifier(imageName, "drawable", packageName)
        image_item_image.setImageResource(imageId)

        //Show gift name
        text_item_name.text = with(data) { getString(getColumnIndex(COLUMN_NAME)) }

        //Set sex to which the gift fits
        val sex = when (with(data) { getInt(getColumnIndex(COLUMN_SEX)) }) {
            0 -> resources.getString(R.string.sex_female)
            1 -> resources.getString(R.string.sex_male)
            -1 -> resources.getString(R.string.sex_any)
            else -> ""
        }

        text_sex.text = getString(R.string.gift_list_activity_sex, sex)

        //Show min and max age
        val ageMin = with(data) { getInt(getColumnIndex(COLUMN_AGE_MIN)) }
        val ageMax = with(data) { getInt(getColumnIndex(COLUMN_AGE_MAX)) }
        text_age.text = getString(R.string.gift_list_activity_age, ageMin, ageMax)

        //Show min and max price
        val priceMin = with(data) { getInt(getColumnIndex(COLUMN_PRICE_MIN)) }
        val priceMax = with(data) { getInt(getColumnIndex(COLUMN_PRICE_MAX)) }
        text_price.text = getString(R.string.gift_list_activity_price, priceMin, priceMax)

        //Show reasons to which the gift fits
        var reasons = ""
        with(data) {
            if (getInt(getColumnIndex(COLUMN_REASON_ANY)) == 1) {
                reasons = resources.getString(R.string.reason_any)
            } else {
                if (getInt(getColumnIndex(COLUMN_REASON_23_FEB)) == 1) {
                    reasons += resources.getString(R.string.reason_23_feb)
                }
                if (getInt(getColumnIndex(COLUMN_REASON_8_MAR)) == 1) {
                    reasons += resources.getString(R.string.reason_8_mar)
                }
                if (getInt(getColumnIndex(COLUMN_REASON_BIRTHDAY)) == 1) {
                    if (!reasons.isEmpty()) {
                        reasons += COMMA
                    }
                    reasons += resources.getString(R.string.reason_birthday)
                }
                if (getInt(getColumnIndex(COLUMN_REASON_NEW_YEAR)) == 1) {
                    if (!reasons.isEmpty()) {
                        reasons += COMMA
                    }
                    reasons += resources.getString(R.string.reason_new_year)
                }
                if (getInt(getColumnIndex(COLUMN_REASON_VALENTINES_DAY)) == 1) {
                    if (!reasons.isEmpty()) {
                        reasons += COMMA
                    }
                    reasons += resources.getString(R.string.reason_valentines_day)
                }
                if (getInt(getColumnIndex(COLUMN_REASON_WEDDING)) == 1) {
                    if (!reasons.isEmpty()) {
                        reasons += COMMA
                    }
                    reasons += resources.getString(R.string.reason_wedding)
                }
            }
            text_reasons.text = resources.getString(R.string.gift_list_activity_reason, reasons)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {}
}
