package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.database.CommonData;

/**
 * HOME Screen Where all action happens
 */
public class HomeActivty extends BaseActivity {
    private Button btnSkip;
    private ImageView ivToolbarbtn, ivMenu;
    private TextView tvLogout;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        init();
        ivMenu.setVisibility(View.VISIBLE);
        ivToolbarbtn.setVisibility(View.GONE);
        btnSkip.setVisibility(View.GONE);
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvLogout:
                CommonData.clearData();
                startActivity(new Intent(HomeActivty.this, SplashActivity.class));
                finish();
                break;
            case R.id.ivMenu:

                break;
            default:
                break;
        }
    }

    /**
     * initialize all variables
     */
    private void init() {
        btnSkip = (Button) findViewById(R.id.btnskip);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        ivToolbarbtn = (ImageView) findViewById(R.id.ivToolbarBtn);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
    }
}
