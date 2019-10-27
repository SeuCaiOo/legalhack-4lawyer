package br.com.app4lawyer.ui.send

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app4lawyer.MainActivity
import br.com.app4lawyer.R
import br.com.app4lawyer.databinding.SendDocFragmentBinding
import br.com.app4lawyer.model.StatusLoad
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SendDocFragment : Fragment() {
    private val app: Application by lazy { requireNotNull(this.activity).application }

    companion object {
        const val REQUEST_DOC_GALLERY = 33

        fun newInstance() = SendDocFragment()
    }

    private lateinit var viewModel: SendDocViewModel
    private lateinit var binding: SendDocFragmentBinding
    private val parentActivity by lazy { (activity as MainActivity) }



    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("--> onCreate $this")
        super.onCreate(savedInstanceState)
        parentActivity.supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("--> onCreateView $this")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.send_doc_fragment,
            container, false
        )

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("--> onActivityCreated $this")
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = SendDocViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SendDocViewModel::class.java)
        binding.sendDocViewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnSend.setOnClickListener {
            changeStatus()
            Handler().postDelayed({
                changeStatus(false)
            findNavController().navigate(R.id.documentFragment)
            }, 2000)
//            pickDocumentIntent()
        }


    }

    fun changeStatus(load: Boolean = true) {
        if (load) binding.loadingSpinner.visibility = View.VISIBLE else View.VISIBLE
    }



    /** Galeria */

    private fun pickDocumentIntent() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            startActivityForResult(
                this,
                REQUEST_DOC_GALLERY
            )
        }
    }

}
