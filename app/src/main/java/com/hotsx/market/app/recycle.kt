package com.hotsx.market.app

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by XJL on 2017/6/2.
 *
 * BaseRecyclerAdapter
 */
abstract class BaseRecyclerAdapter<T>(private val context: Context, private val layoutId: Int,
                                      val data: MutableList<T> = ArrayList()) : RecyclerView.Adapter<MyRecyclerViewHolder>() {

    private lateinit var mView: View
    protected lateinit var mGroup: ViewGroup

    override fun onCreateViewHolder(group: ViewGroup, point: Int): MyRecyclerViewHolder {
        mGroup = group
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = inflater.inflate(layoutId, group, false)
        return MyRecyclerViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, point: Int) {
        holder.mPosition = point
        convert(holder, point, data[point])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    abstract fun convert(holder: MyRecyclerViewHolder, point: Int, t: T)

}

/**
 * 对ViewHolder的封装
 */
@Suppress("UNCHECKED_CAST")
class MyRecyclerViewHolder(private var mConvertView: View) : RecyclerView.ViewHolder(mConvertView) {

    private val mViews: SparseArray<View> = SparseArray()
    var mPosition: Int = 0
    fun <T : View> getView(@IdRes viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun setOnClickListener(clickListener: ListenerWithPosition.OnClickWithPositionListener, @IdRes vararg viewIds: Int): MyRecyclerViewHolder {
        val listener = ListenerWithPosition(mPosition, this)
        listener.setOnClickListener(clickListener)
        viewIds.map { getView<View>(it) }
                .forEach { it.setOnClickListener(listener) }
        return this
    }

    fun setOnLongClickListener(longClickListener: LongClickListenerWithPosition.OnLongClickWithPositionListener, @IdRes vararg viewIds: Int): MyRecyclerViewHolder {
        val listener = LongClickListenerWithPosition(mPosition, this)
        listener.setOnLongClickListener(longClickListener)
        viewIds.map { getView<View>(it) }
                .forEach { it.setOnLongClickListener(listener) }
        return this
    }

}

/**
 * 添加Item点击事件
 */
class ListenerWithPosition(private var mPosition: Int, private var mHolder: MyRecyclerViewHolder) : View.OnClickListener {

    private lateinit var mOnClickListener: OnClickWithPositionListener

    override fun onClick(v: View) {
        mOnClickListener.onClick(v, mPosition, mHolder)
    }

    interface OnClickWithPositionListener {
        fun onClick(v: View, position: Int, holder: MyRecyclerViewHolder)
    }

    fun setOnClickListener(onClickListener: OnClickWithPositionListener) {
        mOnClickListener = onClickListener
    }
}

/**
 * item长按事件
 */
class LongClickListenerWithPosition(private var mPosition: Int, private var mHolder: MyRecyclerViewHolder) : View.OnLongClickListener {

    private lateinit var mOnLongClickListener: OnLongClickWithPositionListener

    override fun onLongClick(v: View): Boolean {
        mOnLongClickListener.onLongClick(v, mPosition, mHolder)
        return true
    }

    interface OnLongClickWithPositionListener {
        fun onLongClick(v: View, position: Int, holder: MyRecyclerViewHolder)
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickWithPositionListener) {
        mOnLongClickListener = onLongClickListener
    }

}

class WrapContentLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

    }
}