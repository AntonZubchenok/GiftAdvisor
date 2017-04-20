package com.zubchenok.giftadvisor.activity

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.zubchenok.giftadvisor.R
import com.zubchenok.giftadvisor.data.*

class ItemActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        //Достаём из интента id подарка и помещаем его в Bundle, который отправится в Loader
        val giftId = intent.getIntExtra(_ID, -1)
        val bundle = Bundle(1)
        bundle.putInt(_ID, giftId)

        //Стартуем Loader, который достанет из БД данные о выбранном подарке и отобразит их
        loaderManager.restartLoader<Cursor>(0, bundle, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {

        //Достаём из БД данные о выбранном подарке
        val selection = "($_ID=?)"
        val selectionArgs = arrayOf(args.getInt(_ID).toString())
        return CursorLoader(this, TABLE_URI, null, selection, selectionArgs, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        val imageView = findViewById(R.id.image_item_image) as ImageView
        val textViewName = findViewById(R.id.text_item_name) as TextView
        val textViewSex = findViewById(R.id.text_sex) as TextView
        val textViewAge = findViewById(R.id.text_age) as TextView
        val textViewPrice = findViewById(R.id.text_price) as TextView
        val textViewReasons = findViewById(R.id.text_reasons) as TextView
        data.moveToFirst()

        //Отображаем изображение с подарком
        val imageName = data.getString(data.getColumnIndex(COLUMN_IMAGE))
        val imageId = this.resources.getIdentifier(imageName, "drawable", this.packageName)
        imageView.setImageResource(imageId)

        //Отображаем название подарка
        textViewName.text = data.getString(data.getColumnIndex(COLUMN_NAME))

        //Отображаем пол, которому подходит подарок
        var sex = ""
        when (data.getInt(data.getColumnIndex(COLUMN_SEX))) {
            0 -> sex = "женский"
            1 -> sex = "мужской"
            -1 -> sex = "любой"
        }
        textViewSex.text = "Пол: " + sex

        //Отображаем минимальный и максимальный возраст
        textViewAge.text = "Возраст: " + data.getInt(data.getColumnIndex(COLUMN_AGE_MIN)) + "-" +
                data.getInt(data.getColumnIndex(COLUMN_AGE_MAX)) + " лет"

        //Отображаем минимальную и максимальную цену
        textViewPrice.text = "Стоимость: " + data.getInt(data.getColumnIndex(COLUMN_PRICE_MIN)) + "-" +
                data.getInt(data.getColumnIndex(COLUMN_PRICE_MAX)) + " BYN"


        //Отображаем поводы, к которым подходит подарок
        var reasons = ""

        if (data.getInt(data.getColumnIndex(COLUMN_REASON_ANY)) == 1) {
            reasons = "любой"
        } else {
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_23_FEB)) == 1) {
                reasons += "23 февраля"
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_8_MAR)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", "
                }
                reasons += "8 марта"
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_BIRTHDAY)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", "
                }
                reasons += "день рождения"
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_NEW_YEAR)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", "
                }
                reasons += "Новый Год"
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_VALENTINES_DAY)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", "
                }
                reasons += "день Святого Валентина"
            }
            if (data.getInt(data.getColumnIndex(COLUMN_REASON_WEDDING)) == 1) {
                if (!reasons.isEmpty()) {
                    reasons += ", "
                }
                reasons += "свадьба"
            }
        }
        textViewReasons.text = "Повод: " + reasons
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {}
}
