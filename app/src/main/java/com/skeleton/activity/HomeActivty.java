package com.skeleton.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.skeleton.R;

/**
 * HOME Screen Where all action happens
 */
public class HomeActivty extends AppCompatActivity {
    private Button btnSkip;
    private ImageView ivToolbarbtn, ivMenu;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        ivMenu.setVisibility(View.VISIBLE);
        ivToolbarbtn.setVisibility(View.GONE);
        init();
    }

    private void init() {
        btnSkip = (Button) findViewById(R.id.btnskip);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        ivToolbarbtn = (ImageView) findViewById(R.id.ivToolbarBtn);
    }
}
