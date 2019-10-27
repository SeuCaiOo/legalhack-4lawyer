package br.com.app4lawyer.ui.send

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

class SendDocViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val service by lazy { Service.MvvmApi.retrofitServiceAPI }

    private val _status = MutableLiveData<StatusLoad>()
    val status: LiveData<StatusLoad>
        get() = _status

    fun changeStatus(result: StatusLoad) {
        _status.value = result
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
        Timber.i("SendDocViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Timber.i("SendDocViewModel destroyed!")
    }

}
