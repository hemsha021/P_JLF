package com.applet.jlf.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.applet.jlf.R;

public class Tutorial extends AppCompatActivity {

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private TutorialViewPagerAdapter tutorialViewPagerAdapter;
    private Button btnNext;

    private String about_title_array[] = {
            "SEE NEARBY BUS STOPS",
            "SEARCH THROUGH BUS ROUTES",
            "SEARCH THROUGH BUS STOPS",
            "PLAN YOUR TRIP"
    };
    private String about_description_array[] = {
            "Select range of bus stops near you.",
            "Select range of bus stops near you.",
            "Select range of bus stops near you.",
            "Select range of bus stops near you.",
    };
    private int about_images_array[] = {
            R.drawable.nearby,
            R.drawable.route,
            R.drawable.stop,
            R.drawable.trip
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial2);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnNext = (Button) findViewById(R.id.btn_next);

        tutorialViewPagerAdapter = new TutorialViewPagerAdapter();
        viewPager.setAdapter(tutorialViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    finish();
                }
            }
        });

        ((ImageButton)findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {

            if (position == about_title_array.length - 1) {
                btnNext.setText("GOT IT");
                btnNext.setBackgroundColor(getResources().getColor(R.color.grey_90));
                btnNext.setTextColor(Color.WHITE);

            } else {
                btnNext.setText("NEXT");
                btnNext.setBackgroundColor(getResources().getColor(R.color.grey_10));
                btnNext.setTextColor(getResources().getColor(R.color.grey_90));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    public class TutorialViewPagerAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        public TutorialViewPagerAdapter(){

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.tutorial_stepper_wizard, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            container.addView(view);


            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}
