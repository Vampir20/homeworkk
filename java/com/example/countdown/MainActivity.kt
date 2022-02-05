package com.example.countdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import room.HistoryActivity
import room.convertMeaningsToString
import kotlin.String as String


class MainActivity : AppCompatActivity() {
    private lateinit var flowOne: Flow<String>
    private lateinit var flowTwo: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFlows()
        zipFlows()
    }

    private fun setupFlows() {
        flowOne = flowOf("Никита", "Инга", "Иван").flowOn(Dispatchers.Default)
        flowTwo = flowOf("Мусса", "Прокофьева", "Грозный").flowOn(Dispatchers.Default)
    }

    private fun zipFlows() {
        findViewById<Button>(R.id.button).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.zip(flowTwo)
                { firstString, secondString ->
                    "$firstString $secondString"
                }.collect {
                    Log.d(TAG, it)
                }
            }
        }
    }

    companion object {
        private const val TAG = "###"
    }

    private val adapter: MainAdapter by lazy {
        MainAdapter(onListItemClickListener)
    }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnlistItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings[0].imageUrl
                    )
                )
            }

        }

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings[0].imageUrl
                    )
                )
            }
        }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}