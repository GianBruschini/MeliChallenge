package com.meli.melichallenge.presentation.search

import android.os.Bundle
import com.meli.melichallenge.presentation.base.BaseActivity
import com.meli.melichallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
    }
}