package com.rafal.text_detection_using_camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.rafal.text_detection_using_camera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var imageBitmap: Bitmap? = null
    val REQUEST_PERMISSION = 101
    private val PERMISSIONS_REQUEST_IMAGE: Int = 1
    private lateinit var _binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        requestForPermission()

        _binding.buttonTakePhoto.setOnClickListener {
            if (checkForPermission()) {
                getImage()
            } else {
                requestForPermission()
            }
        }
        _binding.buttonDetectText.setOnClickListener {
            detectText()
        }
    }

    private fun detectText() {
        val inputImage = imageBitmap?.let { InputImage.fromBitmap(it, 0) }
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val result: Task<Text>? = inputImage?.let {
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
                        if (blockText.isNullOrEmpty())
                            Toast.makeText(this, "No Text was found",Toast.LENGTH_SHORT).show()
                        _binding.textView.text = blockText
                    }
                }
            }
        }?.addOnFailureListener {
            Toast.makeText(this, "Fail to Load Image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkForPermission(): Boolean {
        val permission: Int = ContextCompat.checkSelfPermission(this, CAMERA_SERVICE)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION
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
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getImage()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, PERMISSIONS_REQUEST_IMAGE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSIONS_REQUEST_IMAGE && resultCode == RESULT_OK) {
            val bundleExtra: Bundle? = data?.extras
            imageBitmap = bundleExtra?.get("data") as Bitmap?
            _binding.imageView.setImageBitmap(imageBitmap)
        }
    }
}
