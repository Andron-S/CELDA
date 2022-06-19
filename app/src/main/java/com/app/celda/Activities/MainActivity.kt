package com.app.celda.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.app.celda.R
import com.app.celda.databinding.MainScreenBinding
import kotlinx.android.synthetic.main.main_screen.*

class MainActivity : AppCompatActivity(){


    lateinit var binding : MainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.btnHome -> replaceFragment(HomeFragment())
                R.id.btnSearch -> replaceFragment(SearchFragment())
                R.id.btnProfile -> replaceFragment(ProfileFragment())
            }

            return@setOnItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

//    override fun onRestart() {
//        super.onRestart()
//        setContentView(R.layout.main_screen)
//
//        initialization()
//    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}