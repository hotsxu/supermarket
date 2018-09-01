package com.hotsx.market.flow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.hotsx.market.R
import com.hotsx.market.app.WrapContentLinearLayoutManager
import com.hotsx.market.entity.Commodity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(MainViewModel::class.java)
    }

    private val mAdapter by lazy {
        MainRecyclerAdapter(this)
    }

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        val refreshedToken = FirebaseInstanceId.getInstance().token
//        Log.v("hotsx-token", refreshedToken)

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
            val countries = arrayOf("欧莱雅", "清扬", "海飞丝", "水泥")
            AlertDialog.Builder(this)
                    .setSingleChoiceItems(countries, 0) { _, point ->
                        groupTv.text = countries[point]
                    }.show()
        }

        areaTv.setOnClickListener {
            val countries = arrayOf("区域 211", "区域 111", "区域 2", "区域 45")
            AlertDialog.Builder(this)
                    .setSingleChoiceItems(countries, 0) { _, point ->
                        areaTv.text = countries[point]
                    }.show()
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
//        alert {
//            var edit: EditText? = null
//            title = if (isAdd) "增加" else "减少"
//            customView {
//                frameLayout {
//                    padding = dip(32f)
//                    edit = editText {
//                        inputType = InputType.TYPE_CLASS_NUMBER
//                    }
//                }
//            }
//            noButton { }
//            yesButton {
//                if (edit?.text.isNullOrEmpty()) {
//                    return@yesButton
//                }
//                val count = if (isAdd) {
//                    commodity.amount + edit?.text.toString().toInt()
//                } else {
//                    commodity.amount - edit?.text.toString().toInt()
//                }
//                if (count < 0) {
//                    Snackbar.make(fab, "数量有误...", Snackbar.LENGTH_SHORT).show()
//                } else {
//                    commodity.amount = count
//                    viewModel.updateCommodity(commodity)
//                }
//            }
//        }.show()
        val editText = EditText(this)
        AlertDialog.Builder(this)
                .setView(editText)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { _, _ ->
                    if (editText.text.isNullOrEmpty()) {
                        return@setPositiveButton
                    }
                    val count = if (isAdd) {
                        commodity.amount + editText.text.toString().toInt()
                    } else {
                        commodity.amount - editText.text.toString().toInt()
                    }
                    if (count < 0) {
                        Snackbar.make(fab, "数量有误...", Snackbar.LENGTH_SHORT).show()
                    } else {
                        commodity.amount = count
                        viewModel.updateCommodity(commodity)
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
