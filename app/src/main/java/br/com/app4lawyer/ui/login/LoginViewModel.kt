package br.com.app4lawyer.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.app4lawyer.data.remote.Service
import br.com.app4lawyer.model.StatusLoad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class LoginViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val service by lazy { Service.MvvmApi.retrofitServiceAPI }

    private val _status = MutableLiveData<StatusLoad>()
    val status: LiveData<StatusLoad>
        get() = _status

    fun changeStatusLoading() {
        _status.value = StatusLoad.LOADING
    }

    fun changeStatusDone() {
        _status.value = StatusLoad.DONE
    }

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String>
        get() = _msg

    private val _erro = MutableLiveData<Boolean>()
    val erro: LiveData<Boolean>
        get() = _erro

    fun onErro() {
        _erro.value = true
    }

    fun onErroComplete() {
        _erro.value = false
    }


    init {
        Timber.i("LoginViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Timber.i("LoginViewModel destroyed!")
    }

}
