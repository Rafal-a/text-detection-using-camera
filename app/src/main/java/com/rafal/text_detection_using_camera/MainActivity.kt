package com.rafal.text_detection_using_camera

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rafal.text_detection_using_camera.data.ImageDatabase
import com.rafal.text_detection_using_camera.databinding.ActivityMainBinding
import com.rafal.text_detection_using_camera.ui.history.HistoryFragment
import com.rafal.text_detection_using_camera.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {
    private val historyFragment = HistoryFragment()
    private lateinit var _binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setContentView(_binding.root)


        addNavigationListener()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        ImageDatabase.getData(applicationContext)
        return super.onCreateView(name, context, attrs)

    }
    private fun addNavigationListener() {
        _binding.bottomNavigationView.setOnItemSelectedListener { item ->
            replaceFragment(
                when (item.itemId) {
                    R.id.fragment_history -> historyFragment
                    R.id.fragment_home ->HomeFragment()
                    else -> return@setOnItemSelectedListener false
                }
            )
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.parent_container, newFragment)
            .addToBackStack(null)
        transaction.commit()
    }


}
