package com.zubchenok.giftadvisor.activities

import android.widget.EditText

fun EditText.getIntValue(editText: EditText): Int {

    if (!editText.text.isEmpty()) {
        return editText.text.toString().toInt()
    } else {
        return 0
    }
}