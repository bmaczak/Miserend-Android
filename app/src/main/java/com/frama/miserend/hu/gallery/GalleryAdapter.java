package com.frama.miserend.hu.gallery;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;

import java.util.List;

public class GalleryAdapter extends PagerAdapter {

    private List<String> imageUrls;

    public GalleryAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = LayoutInflater.from(collection.getContext()).inflate(R.layout.item_gallery_image, collection, false);
        SimpleDraweeView draweeView = view.findViewById(R.id.image);
        draweeView.setImageURI(imageUrls.get(position));
        collection.addView(view);
        return view;
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
        return imageUrls.size();
    }
}
