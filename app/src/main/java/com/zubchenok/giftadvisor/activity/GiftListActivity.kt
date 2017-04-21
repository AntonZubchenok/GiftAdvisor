package com.zubchenok.giftadvisor.activity

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.zubchenok.giftadvisor.*
import com.zubchenok.giftadvisor.data.*
import kotlinx.android.synthetic.main.activity_gift_list.*

class GiftListActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>,
        GiftCursorRecyclerViewAdapter.OnItemClickListener {

    //TODO тут можно лэйтинит, старайся избегать этих знаков вопроса
    var mAdapter: GiftCursorRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_list)

        val bundle = makeBundleFromIntent()
        loaderManager.restartLoader(0, bundle, this)

    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {

        //get data from Bundle
        val reasonSpinnerPosition = args.getInt(EXTRA_REASON_SPINNER_POSITION)
        val sex = args.getInt(EXTRA_SEX).toString()
        val age = args.getInt(EXTRA_AGE).toString()
        val maxPrice = args.getInt(EXTRA_MAX_PRICE).toString()

        //Columns to get from database
        val projection = arrayOf(_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_PRICE_MIN, COLUMN_PRICE_MAX)

        //Selection by entered parameters (reason, age, sex, price)
        val selection = makeSelection(reasonSpinnerPosition)

        //Values of selection arguments
        val selectionArgs = arrayOf(sex, age, age, maxPrice)

        return CursorLoader(this, TABLE_URI, projection, selection, selectionArgs, null)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        this.mAdapter?.swapCursor(null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {

        //TODO вот тут что-то странное, let в помощь, и почему это не глобальный адаптер, он нигде не записывается, в вверху вызывается, но так как он нул ничего не отрабатывает
        //Set Adapter on RecyclerView
        val mAdapter = GiftCursorRecyclerViewAdapter(this)
        with(recyclerview_gift_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.swapCursor(data)
    }

    override fun onItemClicked(cursor: Cursor) {

        //Get gift id from cursor
        val giftId = cursor.getInt(cursor.getColumnIndex(_ID))

        //Put gift id into Intent and send it to ItemActivity
        with(Intent(this, ItemActivity::class.java)) {
            putExtra(_ID, giftId)
            startActivity(this)
        }
    }

    /*The method extracts entered in MainActivity data from Intent and puts is into
     bundle that will be sent into Loader*/
    private fun makeBundleFromIntent(): Bundle {
        val bundle = Bundle()
        with(bundle) {
            putInt(EXTRA_REASON_SPINNER_POSITION, intent.getIntExtra(EXTRA_REASON_SPINNER_POSITION, -1))
            putInt(EXTRA_SEX, intent.getIntExtra(EXTRA_SEX, -2))
            putInt(EXTRA_AGE, intent.getIntExtra(EXTRA_AGE, -1))
            putInt(EXTRA_MAX_PRICE, intent.getIntExtra(EXTRA_MAX_PRICE, -1))
        }
        return bundle
    }

    private fun makeSelection(reasonSpinnerPosition: Int): String {

        /*Selection by reason. It gets gifts both for any reason and for selected
       reason (reasonAny and reasonWedding for example).*/
        //TODO reason_any в константы
        val reasonSelection = when (reasonSpinnerPosition) {
            SPINNER_POSITION_BIRTHDAY -> "reason_any=1 OR $COLUMN_REASON_BIRTHDAY=1"
            SPINNER_POSITION_NEW_YEAR -> "reason_any=1 OR $COLUMN_REASON_NEW_YEAR=1"
            SPINNER_POSITION_WEDDING -> "reason_any=1 OR $COLUMN_REASON_WEDDING=1"
            SPINNER_POSITION_8_MAR -> "reason_any=1 OR $COLUMN_REASON_8_MAR=1"
            SPINNER_POSITION_23_FEB -> "reason_any=1 OR $COLUMN_REASON_23_FEB=1"
            SPINNER_POSITION_VALENTINES_DAY -> "reason_any=1 OR $COLUMN_REASON_VALENTINES_DAY=1"
            else -> "reason_any=1"
        }

        //Selection by entered reason, sex, age and price
        val selection = "($COLUMN_SEX=? OR $COLUMN_SEX=-1) AND $COLUMN_AGE_MAX>=? AND " +
                "$COLUMN_AGE_MIN<=? AND $COLUMN_PRICE_MAX<=? AND($reasonSelection)"

        return selection
    }

}
