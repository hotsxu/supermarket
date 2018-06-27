package com.pemt.market.flow

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.pemt.market.R
import com.pemt.market.app.WrapContentLinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(MainViewModel::class.java)
    }

    private val adapter by lazy {
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
        recyclerView.adapter = adapter
    }

    private fun initializeObserve() {
        viewModel.commodityLiveData.observeForever {
            it?.let {
                adapter.data.clear()
                adapter.data.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
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
