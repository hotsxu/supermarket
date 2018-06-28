package com.pemt.market.flow

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.pemt.market.R
import com.pemt.market.app.WrapContentLinearLayoutManager
import com.pemt.market.entity.Commodity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.textInputEditText

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(MainViewModel::class.java)
    }

    private val mAdapter by lazy {
        MainRecyclerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializeView()
        initializeObserve()
    }

    private fun initializeView() {
        fab.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
            integrator.initiateScan()
        }

        recyclerView.layoutManager = WrapContentLinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        mAdapter.setOnChangeClickListener(onChange)

        groupTv.setOnClickListener {
            val countries = listOf("欧莱雅", "清扬", "海飞丝", "水泥")
            selector("柜组选择", countries) { _, i ->
                groupTv.text = countries[i]
            }
        }

        areaTv.setOnClickListener {
            val countries = listOf("区域 211", "区域 111", "区域 2", "区域 45")
            selector("区域选择", countries) { _, i ->
                groupTv.text = countries[i]
            }
        }

    }

    private fun initializeObserve() {
        viewModel.commodityLiveData.observeForever {
            it?.let {
                mAdapter.data.clear()
                mAdapter.data.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private val onChange = fun(isAdd: Boolean, commodity: Commodity) {
        alert {
            var edit: TextInputEditText? = null
            title = if (isAdd) "增加" else "减少"
            customView {
                frameLayout {
                    padding = dip(32f)
                    edit = textInputEditText {
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                }
            }
            noButton { }
            yesButton {
                if (edit?.text.isNullOrEmpty()) {
                    return@yesButton
                }
                val count = if (isAdd) {
                    commodity.amount + edit?.text.toString().toInt()
                } else {
                    commodity.amount - edit?.text.toString().toInt()
                }
                if (count < 0) {
                    snackbar(fab, "数量有误...").show()
                } else {
                    commodity.amount = count
                    viewModel.updateCommodity(commodity)
                }
            }
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        result?.contents?.let {
            Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            viewModel.saveCommodity(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
