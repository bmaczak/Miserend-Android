package com.frama.miserend.hu.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
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
    public boolean onDependentViewChanged(CoordinatorLayout parent, View view, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = view.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) toolbarHeight;
            view.setTranslationY(distanceToScroll * ratio);
        }
        return true;
    }

}