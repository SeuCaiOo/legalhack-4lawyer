package br.com.app4lawyer.ui.choose

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app4lawyer.MainActivity

import br.com.app4lawyer.R
import br.com.app4lawyer.databinding.ChooseFragmentBinding
import br.com.app4lawyer.databinding.DocumentFragmentBinding
import br.com.app4lawyer.model.StatusLoad
import br.com.app4lawyer.ui.send.SendDocFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ChooseFragment : Fragment() {

    private val app: Application by lazy { requireNotNull(this.activity).application }

    companion object {
        fun newInstance() = ChooseFragment()
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var viewModel: ChooseViewModel
    private lateinit var binding: ChooseFragmentBinding
    private val parentActivity by lazy { (activity as MainActivity) }

    private var currentPhotoPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.choose_fragment,
            container, false
        )


        binding.btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }


        binding.btnDigitar.setOnClickListener {
            findNavController().navigate(R.id.sendDocFragment)
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChooseViewModel::class.java)
        binding.chooseViewModel = viewModel


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                with(requireContext()) {
                    when (requestCode) {
                        REQUEST_IMAGE_CAPTURE -> {

                            changeStatus()
                            Handler().postDelayed({
                                changeStatus(false)
                                findNavController().navigate(R.id.documentFragment)
                            }, 2000)

//                            val imageBitmap = data?.extras?.get("data") as Bitmap
//                            imageView.setImageBitmap(imageBitmap)

                        }
                        else -> {

                        }
                    }
                }
            }
            Activity.RESULT_CANCELED -> {

            }
        }

    }


    fun changeStatus(load: Boolean = true) {
        if (load) binding.loadingSpinner.visibility = View.VISIBLE else View.VISIBLE
    }

    /** Câmera */


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    /*private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }*/

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


}
