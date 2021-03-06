package com.zubchenok.giftadvisor.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.zubchenok.giftadvisor.*
import com.zubchenok.giftadvisor.data.SEX_ANY
import com.zubchenok.giftadvisor.data.SEX_FEMALE
import com.zubchenok.giftadvisor.data.SEX_MALE
import kotlinx.android.synthetic.main.activity_main.*
import rx.subjects.PublishSubject

class MainActivity : AppCompatActivity() {

    private val AGE_MIN_VALUE = 10
    private val AGE_MAX_VALUE = 100
    private val AGE_DEFAULT_VALUE = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSeekBar()
        setButtonListener()
    }

    /* The method sets min, max and default values for the age SeekBar and maps SeekBar progress on
     the age TextView */
    private fun setSeekBar() {

        //Set max value
        seekbar_age.max = AGE_MAX_VALUE - AGE_MIN_VALUE

        //Set default value
        text_age_value.text = AGE_DEFAULT_VALUE.toString()
        seekbar_age.progress = AGE_DEFAULT_VALUE

        //Set callbacks
        seekbar_age.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                text_age_value.text = (AGE_MIN_VALUE + progress).toString()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                text_age_value.text = (AGE_MIN_VALUE + seekBar.progress).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
        })
    }

    /* The method sets OnClickListener to the Button which reads input data from UI, puts it into
    * intent and sends it to GiftListActivity*/
    private fun setButtonListener() {
        with(PublishSubject.create<Boolean>()) {
            subscribe { sendIntent() }
            button_find.setOnClickListener({
                onNext(true)
            })
        }
    }

    private fun getSexConstant(): Int {
        return when (radiogroup_sex.checkedRadioButtonId) {
            R.id.radiobutton_male -> SEX_MALE
            R.id.radiobutton_female -> SEX_FEMALE
            else -> SEX_ANY
        }
    }

    private fun sendIntent() {

        //get selected Spinner item position
        val reasonSpinnerPosition = spinner_holidays.selectedItemPosition

        //get sex constant from selected RadioButton
        val sex = getSexConstant()

        //get entered age from SeekBar
        val age = seekbar_age.progress

        //get entered max price from EditText
        val maxPrice = with(edittext_price) { getIntValue(this) }

        with(Intent(this, GiftListActivity().javaClass)) {
            putExtra(EXTRA_REASON_SPINNER_POSITION, reasonSpinnerPosition)
            putExtra(EXTRA_SEX, sex)
            putExtra(EXTRA_AGE, age)
            putExtra(EXTRA_MAX_PRICE, maxPrice)
            startActivity(this)
        }
    }
}


