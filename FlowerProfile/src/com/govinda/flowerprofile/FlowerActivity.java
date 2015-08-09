package com.govinda.flowerprofile;

import com.govinda.profilelistview.ProfileScollListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class FlowerActivity extends Activity {
	 private ProfileScollListView mListView;
	    private ImageView mImageView;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_flower);

	        mListView = (ProfileScollListView) findViewById(R.id.layout_listview);
	        View header = LayoutInflater.from(this).inflate(R.layout.sample_listview_header, null);
	       
	        /**
	         * Setting Image for header 
	         */
	        
	        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
	        mListView.setZoomRatio(ProfileScollListView.ZOOM_X2);
	        mListView.setParallaxImageView(mImageView);
	        mListView.addHeaderView(header);

	        /**
	         * Adapter For bind
	         */
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_expandable_list_item_1,
	                new String[]{
	                        "Anemone",
	                        "Amaryllis",
	                        "Amaranth",
	                        "Aster",
	                        "Azalea",
	                        "Begonia",
	                        "Bellflower",
	                        "Bergamot",
	                        "Bird of Paradise",
	                        "Bluebell",
	                        "Bottlebrush",
	                        "Buttercup",
	                        "Camellias",
	                        "Carnation",
	                        "Cherry Blossom",
	                        "Chrysantemum"
	                }
	        );
	        mListView.setAdapter(adapter);
	        
	    }
	}
