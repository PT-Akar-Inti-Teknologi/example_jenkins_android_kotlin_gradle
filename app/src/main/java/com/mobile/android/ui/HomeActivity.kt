package com.mobile.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mobile.android.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {
    private val binding: ActivityHomeBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}