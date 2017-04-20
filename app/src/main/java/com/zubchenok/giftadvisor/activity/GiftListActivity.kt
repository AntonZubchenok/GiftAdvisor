package com.zubchenok.giftadvisor.activity

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zubchenok.giftadvisor.*
import com.zubchenok.giftadvisor.data.*

class GiftListActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>,
        GiftCursorRecyclerViewAdapter.OnItemClickListener {

    lateinit var mAdapter: GiftCursorRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_list)

        val bundle = makeBundleFromIntent()

        loaderManager.restartLoader(0, bundle, this)

    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {

        //Достаём данные из Bundle
        val reasonSpinnerPosition = args.getInt(EXTRA_REASON_SPINNER_POSITION)
        val sex = args.getInt(EXTRA_SEX).toString()
        val age = args.getInt(EXTRA_AGE).toString()
        val maxPrice = args.getInt(EXTRA_MAX_PRICE).toString()

        //Столбцы, которые нужно достать из БД
        val projection = arrayOf<String>(_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE_MIN, COLUMN_PRICE_MAX)

        //Формирование SQL-запроса для того, чтобы достать из БД подарки, подходящие как
        //для всех праздников, так и для нужного выбранного (reasonAny и reasonWedding например)
        var reasonSelection = "reason_any=1"
        when (reasonSpinnerPosition) {
            SPINNER_POSITION_BIRTHDAY -> reasonSelection += " OR $COLUMN_REASON_BIRTHDAY=1"
            SPINNER_POSITION_NEW_YEAR -> reasonSelection += " OR $COLUMN_REASON_NEW_YEAR=1"
            SPINNER_POSITION_WEDDING -> reasonSelection += " OR $COLUMN_REASON_WEDDING=1"
            SPINNER_POSITION_8_MAR -> reasonSelection += " OR $COLUMN_REASON_8_MAR=1"
            SPINNER_POSITION_23_FEB -> reasonSelection += " OR $COLUMN_REASON_23_FEB=1"
            SPINNER_POSITION_VALENTINES_DAY -> reasonSelection += " OR $COLUMN_REASON_VALENTINES_DAY=1"
        }

        //Условия выборки данных из БД
        val selection = "(" + COLUMN_SEX + "=? OR " + COLUMN_SEX + "=-1) AND " +
                COLUMN_AGE_MAX + ">=? AND " +
                COLUMN_AGE_MIN + "<=? AND " +
                COLUMN_PRICE_MAX + "<=? AND " +
                "(" + reasonSelection + ")"

        //Значения условий выбора
        val selectionArgs = arrayOf(sex, age, age, maxPrice)

        return CursorLoader(this, TABLE_URI, projection, selection, selectionArgs, null)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        this.mAdapter.swapCursor(null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        //Ставим адаптер на RecyclerView
        val mAdapter = GiftCursorRecyclerViewAdapter(this)
        val recyclerView = findViewById(R.id.recyclerview_gift_list) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mAdapter

        mAdapter.swapCursor(data)
    }

    /*The method extracts entered in MainActivity data from Intent and puts is into
      bundle that will be sent into Loader*/
    private fun makeBundleFromIntent(): Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt(
                    EXTRA_REASON_SPINNER_POSITION,
                    intent.getIntExtra(EXTRA_REASON_SPINNER_POSITION, -1))
            putInt(EXTRA_SEX, intent.getIntExtra(EXTRA_SEX, -2))
            putInt(EXTRA_AGE, intent.getIntExtra(EXTRA_AGE, -1))
            putInt(EXTRA_MAX_PRICE, intent.getIntExtra(EXTRA_MAX_PRICE, -1))
        }
        return bundle
    }

    override fun onItemClicked(cursor: Cursor) {
        //Считываем с курсора id подарка
        val giftId = cursor.getInt(cursor.getColumnIndex(_ID))

        //помещаем id в интент и отправляем в ItemActivity
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(_ID, giftId)
        startActivity(intent)
    }

}
