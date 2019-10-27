package br.com.app4lawyer.ui.login

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app4lawyer.MainActivity

import br.com.app4lawyer.R
import br.com.app4lawyer.databinding.LoginFragmentBinding
import br.com.app4lawyer.model.StatusLoad
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class LoginFragment : Fragment() {
    private val app: Application by lazy { requireNotNull(this.activity).application }

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private val parentActivity by lazy { (activity as MainActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("--> onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("--> onCreateView $this")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container, false
        )
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("--> onActivityCreated $this")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel


        binding.btnLogin.setOnClickListener {
//            viewModel.changeStatusLoading()

            changeStatus()
            Handler().postDelayed({
                changeStatus(false)
//                viewModel.changeStatusDone()
                findNavController().navigate(R.id.chooseFragment)
            }, 2000)

        }


    }

    fun changeStatus(load: Boolean = true) {
        if (load) binding.loadingSpinner.visibility = View.VISIBLE else View.VISIBLE
    }

}
