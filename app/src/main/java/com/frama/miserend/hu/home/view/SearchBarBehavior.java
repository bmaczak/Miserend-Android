package com.frama.miserend.hu.home.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.frama.miserend.hu.utils.ViewUtils;

/**
 * Created by maczak on 2018. 02. 14..
 */

public class SearchBarBehavior extends CoordinatorLayout.Behavior<View> {

    private int toolbarHeight;

    public SearchBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = ViewUtils.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View fab, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int topMargin = lp.topMargin;
            int distanceToScroll = child.getHeight() + topMargin;
            float ratio = dependency.getY() / (float) toolbarHeight;
            child.setTranslationY(distanceToScroll * ratio);
        }
        return true;
    }

}