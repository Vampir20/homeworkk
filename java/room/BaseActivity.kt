package room

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.countdown.R


private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {
    private lateinit var binding: LoadingLayoutBinding
    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persinstentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persinstentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)

        isNetworkAvailable = isOnline(applicationContext)

        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()

        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is Appstate.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_title_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is Appstate.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visisbility = View.VISIBLE
                    binding.progressBarRound.visisbility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visisbility = View.GONE
                    binding.progressBarRound.visisbility = View.VISIBLE
                }
            }

            is Appstate.Error -> {
                showViewWorking()
                showAlertDialog(
                    getString(R.string.error_stub),
                    appState.error.message
                )
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?){
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }
    private fun showViewWorking(){
        binding.LoadingFragment.visisbility = View.GONE
    }
    private fun showViewLoading(){
        binding.LoadingFragment.visisbility = View.VISIBLE
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null

    }

    abstract fun setDataToAdapter(data: List<DataModel>)
}