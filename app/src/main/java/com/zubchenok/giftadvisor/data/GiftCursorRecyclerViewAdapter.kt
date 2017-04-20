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

        fun bindData(cursor: Cursor, listener: OnItemClickListener) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
            val imageId = this.itemView.context.resources.getIdentifier(
                    image, "drawable", this.itemView.context.packageName)

            itemView.text_gift_name.text = name
            itemView.image_gift.setImageResource(imageId)
            itemView.setOnClickListener { listener.onItemClicked(cursor) }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(cursor: Cursor)
    }

//    class GiftViegwHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        @BindView(R.id.image_gift)
//        internal var image: ImageView? = null
//        @BindView(R.id.text_gift_name)
//        internal var name: TextView? = null
//
//        init {
//            ButterKnife.bind(this, itemView)
//        }
//
//        fun bindData(cursor: Cursor, listener: OnItemClickListener) {
//            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
//            val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
//            val imageId = this.image!!.context.resources.getIdentifier(image, "drawable",
//                    this.image!!.context.packageName)
//
//            this.name!!.text = name
//            this.image!!.setImageResource(imageId)
//
//            itemView.setOnClickListener { listener.onItemClicked(cursor) }
//        }
//    }
}