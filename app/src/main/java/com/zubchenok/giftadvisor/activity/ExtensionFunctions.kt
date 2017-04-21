package com.zubchenok.giftadvisor.activity

import android.widget.EditText

fun EditText.getIntValue(editText: EditText): Int {

    if (!editText.text.isEmpty()) {
        return editText.text.toString().toInt()
    } else {
        return 0
    }
}