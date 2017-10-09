package com.qiangxi.populareffect.activity.viewpager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.qiangxi.populareffect.R;
import com.qiangxi.populareffect.databinding.ActivityCostomeViewPager02Binding;
import com.qiangxi.populareffect.fragment.ViewPagerFragment;

/**
 * 测试ViewPager嵌套ViewPager，发现并无滑动冲突，即：<br/>
 * - 如果内部ViewPager处在第一页或最后一页，再次滑动时，外部的ViewPager可以正常响应手势<br/>
 * - 如果内部ViewPager处在中间的某一页，左右滑动时，只有内部ViewPager会响应手势，外部的ViewPager不会响应手势<br/>
 * <p>
 * <br/>并不会 发生内外两个ViewPager滑动冲突的情况<br/>
 * <p>
 * 测试条件：<br/>
 * - 外部ViewPager+fragment<br/>
 * - 内部ViewPager+View<br/>
 */
public class CustomViewPagerActivity02 extends AppCompatActivity {
    private ActivityCostomeViewPager02Binding mBinding;
    private String[] arr = {"Activity02第一页", "Activity02第二页"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CustomViewPagerActivity02");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_costome_view_pager02);

        mBinding.outerTitle.setText(arr[0]);
        mBinding.outerPager.setAdapter(new OuterPagerAdapter(getSupportFragmentManager()));

        mBinding.outerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.outerTitle.setText(arr[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class OuterPagerAdapter extends FragmentPagerAdapter {

        OuterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return arr[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new ViewPagerFragment();
        }

        @Override
        public int getCount() {
            return arr.length;
        }


    }
}
