package com.frama.miserend.hu.home.view

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

import com.frama.miserend.hu.utils.ViewUtils

/**
 * Created by maczak on 2018. 02. 14..
 */

class SearchBarBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private val toolbarHeight: Int

    init {
        this.toolbarHeight = ViewUtils.getToolbarHeight(context)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, fab: View?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        if (dependency is AppBarLayout) {
            val lp = child!!.layoutParams as CoordinatorLayout.LayoutParams
            val topMargin = lp.topMargin
            val distanceToScroll = child.height + topMargin
            val ratio = dependency.y / toolbarHeight.toFloat()
            child.translationY = distanceToScroll * ratio
        }
        return true
    }

}