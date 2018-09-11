package com.frama.miserend.hu.churchdetails.view;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 27..
 */

public class ChurchDetailsGalleryPagerAdapter extends PagerAdapter {

    private List<Image> images;
    private ImageClickedListener imageClickedListener;

    public ChurchDetailsGalleryPagerAdapter(ImageClickedListener imageClickedListener) {
        super();
        this.imageClickedListener = imageClickedListener;
        this.images = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        SimpleDraweeView draweeView = (SimpleDraweeView) LayoutInflater.from(collection.getContext()).inflate(R.layout.item_image, collection, false);
        draweeView.setImageURI(images.get(position).getImageUrl());
        collection.addView(draweeView);
        draweeView.setOnClickListener(view -> imageClickedListener.onImageClicked(position));
        return draweeView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public void setImages(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public interface ImageClickedListener {
        void onImageClicked(int position);
    }
}
