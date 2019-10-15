package com.frama.miserend.hu.home.view

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

import com.frama.miserend.hu.utils.ViewUtils
import com.frama.miserend.hu.utils.getToolbarHeight

/**
 * Created by maczak on 2018. 02. 14..
 */

class BottomNavigationViewBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    private val toolbarHeight: Int = context.getToolbarHeight()

    override fun layoutDependsOn(parent: CoordinatorLayout?, fab: BottomNavigationView?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: BottomNavigationView?, dependency: View?): Boolean {
        if (dependency is AppBarLayout) {
            val lp = child!!.layoutParams as CoordinatorLayout.LayoutParams
            val bottomMargin = lp.bottomMargin
            val distanceToScroll = child.height + bottomMargin
            val ratio = dependency.y / toolbarHeight.toFloat()
            child.translationY = -distanceToScroll * ratio
        }
        return true
    }

}