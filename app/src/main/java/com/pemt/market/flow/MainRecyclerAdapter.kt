package com.pemt.market.flow

import android.content.Context
import android.view.View
import android.widget.TextView
import com.pemt.market.R
import com.pemt.market.app.BaseRecyclerAdapter
import com.pemt.market.app.ListenerWithPosition
import com.pemt.market.app.MyRecyclerViewHolder
import com.pemt.market.entity.Commodity

class MainRecyclerAdapter(context: Context) : BaseRecyclerAdapter<Commodity>(context, R.layout.item_main),
        ListenerWithPosition.OnClickWithPositionListener {

    override fun convert(holder: MyRecyclerViewHolder, point: Int, t: Commodity) {
        holder.run {
            getView<TextView>(R.id.barcodeTv).text = t.barcode
            getView<TextView>(R.id.amountTv).text = t.amount.toString()
            setOnClickListener(this@MainRecyclerAdapter, R.id.item)
        }
    }

    override fun onClick(v: View, position: Int, holder: MyRecyclerViewHolder) {

    }
}