package br.com.app4lawyer.util

import android.view.View
import androidx.databinding.BindingAdapter
import br.com.app4lawyer.model.StatusLoad

object BindingAdapters {

    @BindingAdapter("loadStatus")
    @JvmStatic
    fun bindStatus(view: View, status: StatusLoad?) {
        when (status) {
            StatusLoad.LOADING -> {
                view.visibility = View.VISIBLE
            }
            StatusLoad.ERROR -> {
                view.visibility = View.GONE
            }
            StatusLoad.DONE -> {
                view.visibility = View.GONE
            }
        }
    }
}