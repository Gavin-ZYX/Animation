package com.propertyanimationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * 属性动画测试类
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Thread mThread;

    /**
     * 图片view
     */
    private ImageView imageView;

    /**
     * 透明动画
     */
    public static final String ANIMA_ALPHA = "alpha";

    /**
     * 旋转动画
     */
    public static final String ANIMA_ROTATION = "rotation";

    /**
     * 沿X轴移动动画
     */
    public static final String ANIMA_TRANSLATION_X = "translationX";

    /**
     * 沿Y轴移动动画
     */
    public static final String ANIMA_TRANSLATION_Y = "translationY";

    /**
     * 沿X轴缩放动画
     */
    public static final String ANIMA_SCALE_X = "scaleX";

    /**
     * 沿Y轴缩放动画
     */
    public static final String ANIMA_SCALE_Y = "scaleY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     * 初始化View
     */
    private void initView() {
        imageView = (ImageView) findViewById(R.id.iamgeview);
        setListener(
                R.id.alpha,
                R.id.rotation,
                R.id.translation,
                R.id.scale,
                R.id.set,
                R.id.set_xml);
    }

    /**
     * 设置点击事件
     * @param i 视图id
     */
    private void setListener(@IdRes int... i) {
        for (int item : i) {
            View v = findViewById(item);
            if (v == null) return;
            v.setOnClickListener(this);
        }
    }

    /**
     * 透明度
     */
    private void objectAnimationTest1() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_ALPHA, 1f, 0f, 1f, 0f);
        animator.setDuration(2000);
        animator.start();
        animator.setRepeatCount(-1);

        //从xml加载动画
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.alpha);
//        animator.setTarget(imageView);
//        animator.start();

    }

    /**
     * 旋转
     */
    private void objectAnimationTest2() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_ROTATION, 0f, 360f, 0f);
        animator.setDuration(2000);
        animator.start();

        //从xml加载动画
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.rotation);
//        animator.setTarget(imageView);
//        animator.start();
    }

    /**
     * 移动
     */
    private void objectAnimationTest3() {
        float x = imageView.getTranslationX();//得到的x为0.0f
        //ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_TRANSLATION_Y, x, 300f, x);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_TRANSLATION_X, 0f, -300f, 0f);
        animator.setDuration(2000);
        animator.start();

        //从xml文件加载
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.translation_x);
//        animator.setTarget(imageView);
//        animator.start();
    }

    /**
     * 缩放
     */
    private void objectAnimationTest4() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_SCALE_X, 1f, 2f, 1f);
        //ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_SCALE_Y, 1f, 2f, 1f);
        animator.setDuration(2000);
        animator.start();

        //从xml加载
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scale_x);
//        animator.setTarget(imageView);
//        animator.start();
    }

    /**
     * 组合动画
     * AnimatorSet这个类，这个类提供了一个play()方法，调用后将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
     * after(Animator anim) 将现有动画插入到传入的动画之后执行
     * after(long delay) 将现有动画延迟指定毫秒后执行
     * before(Animator anim) 将现有动画插入到传入的动画之前执行
     * with(Animator anim) 将现有动画和传入的动画同时执行
     */
    private void objectAnimationTest5() {
        //沿x轴放大
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, ANIMA_SCALE_X, 1f, 2f, 1f);
        //沿y轴放大
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, ANIMA_SCALE_Y, 1f, 2f, 1f);
        //移动
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(imageView, ANIMA_TRANSLATION_X, 0f, 200f, 0f);
        //透明动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, ANIMA_ALPHA, 1f, 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        //同时沿X,Y轴放大，且改变透明度，然后移动
        //注意：after和before不能同时使用，只能选其一
        set.play(scaleXAnimator).with(scaleYAnimator).with(animator).before(translationXAnimator);
        //都设置5s
        set.setDuration(3000);

        //添加监听事件
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始的时候调用
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //画结束的时候调用
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //动画被取消的时候调用
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //动画重复执行的时候调用

            }
        });

        //另一种设置监听的方式，里面的监听方法可以选择性重写
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
        });
        set.start();

    }

    /**
     * 组合动画mxl
     */
    private void objectAnimationTest6() {

        //从xml加载
        //向左移动并旋转，然后回到原来的位置
        //接着向右移动并旋转，然后回到原来的位置
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set);
        animator.setTarget(imageView);
        //匀速进行
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(1000);
        animator.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha:
                //透明度动画
                objectAnimationTest1();
                break;
            case R.id.rotation:
                //旋转动画
                objectAnimationTest2();
                break;
            case R.id.translation:
                //移动动画
                objectAnimationTest3();
                break;
            case R.id.scale:
                //缩放动画
                objectAnimationTest4();
                break;
            case R.id.set:
                //组合动画
                objectAnimationTest5();
                break;
            case R.id.set_xml:
                //组合动画
                objectAnimationTest6();
                break;
        }
    }
}
