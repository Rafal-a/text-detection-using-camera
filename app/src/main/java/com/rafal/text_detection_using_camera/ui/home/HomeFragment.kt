package com.rafal.text_detection_using_camera.ui.home


import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.rafal.text_detection_using_camera.BuildConfig
import com.rafal.text_detection_using_camera.R
import com.rafal.text_detection_using_camera.base.BaseFragment
import com.rafal.text_detection_using_camera.databinding.FragmentHomeBinding
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var imageBitmap: Bitmap? = null
    val REQUEST_PERMISSION = 101
    private val PERMISSIONS_REQUEST_IMAGE: Int = 1
    private var uri: Uri? = null
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>? = null

    override val LOG_TAG: String = "hhhh"
    override val viewModel: ViewModel = HomeViewModel()
    override val bindingInflater: (LayoutInflater, Int, ViewGroup?, Boolean) -> FragmentHomeBinding =
        DataBindingUtil::inflate


    override fun setupView() {

        val appPerms =
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )

        activityResultLauncher?.launch(appPerms)
        binding.buttonTakePhoto.setOnClickListener {
            getImage()
        }


        binding.buttonDetectText.setOnClickListener {
            detectText()
            //createImageFile()

        }

        /* viewModel
     .observe(this, EventObserve {
             val nav =
                 CelebrityFragmentDirections.actionCelebrityFragmentToCelebrityDetailsFragment(it)
             Navigation.findNavController(requireView()).navigate(nav)
         })*/


    }


    private fun saveImageUri(imageBitmap: Bitmap) {
        val authority = "${BuildConfig.APPLICATION_ID}.provider"
        var file: File? = null
        try {
            file = createImageFile()

        } catch (ex: IOException) {
            ex.printStackTrace()
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created

        file?.let {
            FileProvider.getUriForFile(
                requireActivity(),
                authority,
                it
            )


        }
        println(file)
    }


    private fun createImageFile(): File? {
        // Create an image file name
        val filename: String = "Text detection image - " + System.currentTimeMillis().toString()
        val storageDir = requireActivity().getExternalFilesDir(MediaStore.Images.Media.DISPLAY_NAME)
        val image = File.createTempFile(
            filename,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        return image
    }

    private fun detectText() {
        val inputImage = imageBitmap?.let { InputImage.fromBitmap(it, 0) }
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        inputImage?.let {
            recognizer.process(it).addOnSuccessListener {
                val builder = StringBuilder()
                for (block in it.textBlocks) {
                    val blockText = block.text
                    val point = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val point = line.cornerPoints
                        val blockFrame = line.boundingBox
                        for (element in line.elements) {
                            val elementText = element.text
                        }
                        binding.textView.text = blockText
                    }
                }
            }
        }?.addOnFailureListener {
            Toast.makeText(requireContext(), "Fail to Load Image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkForPermission(): Boolean {
        val permission: Int = ContextCompat.checkSelfPermission(
            requireContext(),
            AppCompatActivity.CAMERA_SERVICE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ),
            REQUEST_PERMISSION,
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            val cameraPermission: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (cameraPermission) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                getImage()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        uri = intent.data
        try {
            startActivityForResult(intent, PERMISSIONS_REQUEST_IMAGE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSIONS_REQUEST_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            val bundleExtra: Bundle? = data?.extras
            imageBitmap = bundleExtra?.get("data") as Bitmap?
            binding.imageView.setImageBitmap(imageBitmap)
            imageBitmap?.let { saveImageUri(it) }

        }
    }


}
