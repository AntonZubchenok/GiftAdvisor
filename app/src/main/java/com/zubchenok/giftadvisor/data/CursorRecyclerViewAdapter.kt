package com.zubchenok.giftadvisor.data

import android.database.Cursor
import android.support.v7.widget.RecyclerView

abstract class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var cursor: Cursor? = null

    fun swapCursor(cursor: Cursor?) {
        this.cursor = cursor
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (this.cursor != null) this.cursor!!.count else 0

    fun getItem(position: Int): Cursor {
        if (this.cursor != null && !this.cursor!!.isClosed) {
            this.cursor!!.moveToPosition(position)
        }
        return this.cursor as Cursor
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cursor = this.getItem(position)
        this.onBindViewHolder(holder, cursor)
    }

    abstract fun onBindViewHolder(holder: VH, cursor: Cursor)
}