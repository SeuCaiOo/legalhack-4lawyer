package br.com.app4lawyer.ui.document

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
import br.com.app4lawyer.databinding.DocumentFragmentBinding
import br.com.app4lawyer.databinding.LoginFragmentBinding
import br.com.app4lawyer.model.StatusLoad

class DocumentFragment : Fragment() {

    private val app: Application by lazy { requireNotNull(this.activity).application }


    companion object {
        fun newInstance() = DocumentFragment()
    }

    private lateinit var viewModel: DocumentViewModel
    private lateinit var binding: DocumentFragmentBinding
    private val parentActivity by lazy { (activity as MainActivity) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.document_fragment,
            container, false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DocumentViewModel::class.java)
        binding.documentViewModel = viewModel
    }

//    fun changeStatus(load: Boolean = true) {
//        if (load) binding.loadingSpinner.visibility = View.VISIBLE else View.VISIBLE
//    }

}
