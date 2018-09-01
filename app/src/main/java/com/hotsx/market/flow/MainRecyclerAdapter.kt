package com.hotsx.market.flow

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.hotsx.market.R
import com.hotsx.market.app.BaseRecyclerAdapter
import com.hotsx.market.app.ListenerWithPosition
import com.hotsx.market.app.MyRecyclerViewHolder
import com.hotsx.market.entity.Commodity

class MainRecyclerAdapter(private var context: Context) : BaseRecyclerAdapter<Commodity>(context, R.layout.item_main),
        ListenerWithPosition.OnClickWithPositionListener {

    private var onChangeClick: ((Boolean, Commodity) -> Unit)? = null

    override fun convert(holder: MyRecyclerViewHolder, point: Int, t: Commodity) {
        holder.run {
            getView<TextView>(R.id.seqTv).text = t.id.toString()
            getView<TextView>(R.id.barcodeTv).text = t.barcode
            getView<TextView>(R.id.amountTv).text = t.amount.toString()
            getView<ImageButton>(R.id.addIBt).setOnClickListener {
                onChangeClick?.invoke(true, t)
            }
            getView<ImageButton>(R.id.subtractIBt).setOnClickListener {
                onChangeClick?.invoke(false, t)
            }
        }
    }

    override fun onClick(v: View, position: Int, holder: MyRecyclerViewHolder) {

    }

    fun setOnChangeClickListener(method: (Boolean, Commodity) -> Unit) {
        onChangeClick = method
    }
}