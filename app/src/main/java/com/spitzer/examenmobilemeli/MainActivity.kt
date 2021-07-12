package com.spitzer.examenmobilemeli

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.spitzer.examenmobilemeli.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MainActivityTheme);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
    }

    fun showProgressBar() {
        binding.clProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.clProgressBar.visibility = View.GONE
    }
}
