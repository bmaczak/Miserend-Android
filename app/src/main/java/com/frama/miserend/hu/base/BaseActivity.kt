package com.frama.miserend.hu.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import dagger.android.AndroidInjection

/**
 * Created by Balazs on 2018. 02. 19..
 */

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
