package com.zubchenok.giftadvisor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.zubchenok.giftadvisor.data.GiftListActivity
import com.zubchenok.giftadvisor.data.SEX_ANY
import com.zubchenok.giftadvisor.data.SEX_FEMALE
import com.zubchenok.giftadvisor.data.SEX_MALE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Age seekBar constants
    val AGE_MIN_VALUE = 10
    val AGE_MAX_VALUE = 100
    val AGE_DEFAULT_VALUE = 30

    val EXTRA_SEX = "sex"
    val EXTRA_REASON_SPINNER_POSITION = "reason spinner position"
    val EXTRA_AGE = "age"
    val EXTRA_MAX_PRICE = "max price"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSeekBar()
        setButton()
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

    private fun setButton() {

        button_find.setOnClickListener {

            //get selected Spinner item position
            val reasonSpinnerPosition = spinner_holidays.selectedItemPosition

            //get selected RadioButton
            val sex = when (radiogroup_sex.checkedRadioButtonId) {
                R.id.radiobutton_male -> SEX_MALE
                R.id.radiobutton_female -> SEX_FEMALE
                else -> SEX_ANY
            }

            //get entered age from SeekBar
            val age = seekbar_age.progress.toString().toInt()

            //get entered max price from EditText
            var maxPrice = 0
            if (!edittext_price.text.isEmpty()) {
                maxPrice = edittext_price.text.toString().toInt()
            }

            //Create and send Intent with data to GiftListActivity
            val intent = Intent(this, GiftListActivity().javaClass)
            intent.putExtra(EXTRA_REASON_SPINNER_POSITION, reasonSpinnerPosition)
            intent.putExtra(EXTRA_SEX, sex)
            intent.putExtra(EXTRA_AGE, age)
            intent.putExtra(EXTRA_MAX_PRICE, maxPrice)
            startActivity(intent)
        }
    }
}
