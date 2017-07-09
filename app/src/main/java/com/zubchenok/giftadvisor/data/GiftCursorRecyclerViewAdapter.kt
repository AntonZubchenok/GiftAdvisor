package com.zubchenok.giftadvisor.data

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zubchenok.giftadvisor.R
import kotlinx.android.synthetic.main.list_item.view.*

class GiftCursorRecyclerViewAdapter(val listener: OnItemClickListener) :
        CursorRecyclerViewAdapter<GiftCursorRecyclerViewAdapter.GiftViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return GiftViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiftViewHolder, cursor: Cursor) {
        holder.bindData(cursor, listener)
    }

    class GiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(cursor: Cursor, listener: OnItemClickListener): View = itemView.apply {
            text_gift_name.text = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val imageName = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
            val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            image_gift.setImageResource(imageId)
            setOnClickListener { listener.onItemClicked(cursor) }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(cursor: Cursor)
    }
}