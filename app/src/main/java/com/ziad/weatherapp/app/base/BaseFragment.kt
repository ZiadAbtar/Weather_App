package com.ziad.weatherapp.app.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ziad.weatherapp.R
import com.ziad.weatherapp.app.MainActivity
import com.ziad.weatherapp.data.remote.exception.APIException
import com.ziad.weatherapp.data.remote.response.base.BaseResponse
import com.ziad.weatherapp.data.remote.response.base.OnResultReceived
import com.ziad.weatherapp.data.remote.response.base.ResultWrapper
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class BaseFragment<RESPONSE : BaseResponse, V : BaseViewModel> : Fragment(),
    OnResultReceived<RESPONSE> {

    protected lateinit var mActivity: MainActivity
    protected lateinit var mRootView: View

    abstract val getViewModel: Class<V>
    abstract val getLayoutId: Int
    protected lateinit var viewModel: V
    private lateinit var listener: OnResultReceived<RESPONSE>
    private var mProgress: ProgressBar? = null
    abstract fun callPrimaryApi()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mActivity = activity as MainActivity
        mRootView = inflater.inflate(getLayoutId, container, false)
        mProgress = mRootView.findViewById(R.id.pb_loading)
        return mRootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listener = this
        viewModel = ViewModelProvider.NewInstanceFactory().create(getViewModel)
        viewModel.liveData.observe(viewLifecycleOwner, Observer {

            hideProgress()

            when (it) {
                is ResultWrapper.UnhandledException -> {
                    listener.onUnhandledException(it.throwable) { callPrimaryApi() }
                }
                is ResultWrapper.APIError -> {
                    listener.onAPIException(it.exception) { callPrimaryApi() }
                }
                is ResultWrapper.Success -> {
                    listener.onSuccess(it.value as RESPONSE)
                }
            }
        })
    }

    override fun onAPIException(apiException: APIException, callable: () -> Unit) {
        if (apiException.apiError == null) {
            Log.e(this.javaClass.canonicalName, "Status Code in APIException is null")
            return
        }

        Snackbar.make(
            mRootView,
            apiException.apiError?.message ?: getString(R.string.Unexpected_error),
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.Retry) { callable.invoke() }.show()


    }

    override fun onUnhandledException(throwable: Throwable, callable: () -> Unit) {
        var message = getString(R.string.Unexpected_error)
        if (throwable is SocketTimeoutException) {
            message = getString(R.string.Internet_connection_error)
        }

        Snackbar.make(
            mRootView,
            message,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.Retry) { callable.invoke() }.show()
    }


     open fun showProgress() {
        mProgress?.visibility = View.VISIBLE
    }

     open fun hideProgress() {
        mProgress?.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}

