package com.longshihan.donghuakuangjia;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private View mfab;
    private PointF point;
    public final static int secondeAnimation_distance = 200;
    public final static float SCALE_FACTOR = 15f;
    public final static long ANIMATE_TIME = 300;
    private boolean mRevealFlag = false;
    private View fab_container;
    private LinearLayout media_control_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfab = findViewById(R.id.fab);
        fab_container = findViewById(R.id.fab_container);
        media_control_container = (LinearLayout) findViewById(R.id.media_controls_container);
       /* ObjectAnimator oa = ObjectAnimator.ofFloat(mfab,
                "translationX", 300, 200);
        ObjectAnimator oa = ObjectAnimator.ofFloat(mfab,
                "translationY", 300, 200);*/
/*
        ObjectAnimator oa = ObjectAnimator.ofFloat(this,
                "point", new PointF(300,100), new PointF(100,200));*/


        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAnimation(view);
            }
        });

    }

    private void performAnimation(View view) {
        final float startx = mfab.getX();
        float starty = mfab.getY();
        PathEvalutiom pathEvalutiom = new PathEvalutiom(new PointF(-200, 200), new PointF(-600,
                50));
        ObjectAnimator oa = ObjectAnimator.ofObject(this,
                "point", pathEvalutiom, new PointF(-50, -20), new PointF(-600, -20));
        oa.setDuration(ANIMATE_TIME);
        oa.start();

        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (Math.abs(startx - mfab.getX()) > secondeAnimation_distance) {
                    if (!mRevealFlag) {
                        ImageButton fac = (ImageButton) mfab;
                        fac.setImageDrawable(new BitmapDrawable());
                        fab_container.setY(fab_container.getY() + mfab.getY() / 2);
                        //放大动画
                        mfab.animate()
                                .scaleXBy(SCALE_FACTOR)//相对于当前位置在偏移的量0-15,形成水波纹效果
                                .scaleXBy(SCALE_FACTOR)
                                .setListener(listenerAdapter)
                                .setDuration(ANIMATE_TIME);
                        mRevealFlag = true;
                    }

                }
            }
        });
    }

    private ValueAnimator.AnimatorListener listenerAdapter = new ValueAnimator.AnimatorListener() {


        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mfab.setVisibility(View.INVISIBLE);
            fab_container.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            //执行container里面的缩放动画
            for (int i = 0; i < media_control_container.getChildCount(); i++) {
                View childview = media_control_container.getChildAt(i);
                childview.setScaleX(0);
                childview.setScaleY(0);
                childview.animate().scaleX(1)
                        .scaleY(1)
                        .setDuration(ANIMATE_TIME)
                        .setStartDelay(i * 90).start();


            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }


    };

    public void setPoint(PointF point) {
        this.point = point;
        mfab.setTranslationX(point.x);
        if (mRevealFlag) {
            mfab.setTranslationY(point.y - mfab.getHeight() / 2);
        } else {
            mfab.setTranslationY(point.y);
        }
    }
}
