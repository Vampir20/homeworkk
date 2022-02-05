package room

import android.os.Bundle
import android.view.View.inflate
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import java.util.Observer

class HistoryActivity: BaseActivity<AppState, HistoryInteractor>() {
    private lateinit var binding: ActivityHistoryBinding
    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>){
        adapter.setData(data)
    }

    private fun initViewModel(){
        if (binding.historyActivityRecyclerView.adapter != null){
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel = HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState>{
            renderData(it)
        })
    }
    private fun initViews(){
        binding.historyActivityRecyclerView.adapter = adapter
    }
}