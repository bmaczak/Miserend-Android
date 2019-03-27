package com.frama.miserend.hu.base

import android.content.Context
import android.support.v4.app.Fragment

import dagger.android.support.AndroidSupportInjection

/**
 * Created by Balazs on 2018. 02. 19..
 */

open class BaseFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
