## ViewPager滑动跳转

### 难度
一般

### 效果说明
- 当页面已经处于第一页再继续向右滑动时，触发相应回调
- 当页面已经处于最后一页再继续向左滑动时，触发相应回调

该效果有两种常见的使用场景：
1. 用于APP启动页，当处于最后一页时，再继续向左滑动，跳转到主界面（大部分app的启动页都基本有这个效果）
2. 用于轮播图，当轮播图处于最后一页或第一页时，再继续向左或向右滑动，则跳转到其他的tab或界面中。（如建行首页的轮播图，掘金的顶部tab等）

### 效果图
![效果图](https://github.com/qiangxi/PopularEffect/blob/master/gif/GIF004.gif?raw=true)

### 实现原理
继承ViewPager，重写`onTouchEvent(MotionEvent ev)`方法，在该方法中进行左右滑动的判断。
在手指抬起时，根据情况回调相应的方法。

### 核心代码
```java
@Override
public boolean onTouchEvent(MotionEvent ev) {
    PagerAdapter adapter = getAdapter();
    if (adapter == null) return super.onTouchEvent(ev);
    int currentItem = getCurrentItem();
    int count = adapter.getCount();
    switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mLastX = (int) ev.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            float diffX = ev.getX() - mLastX;
            if (Math.abs(diffX) < mTouchSlop) break;
            dispatchEdge(currentItem, count, diffX);
            break;
        case MotionEvent.ACTION_UP:
            dispatchListener(currentItem);
            break;
    }
    return super.onTouchEvent(ev);
}
```

### 样例路径
[CustomViewPager](https://github.com/qiangxi/PopularEffect/blob/master/app/src/main/java/com/qiangxi/populareffect/view/CustomViewPager.java)

[CustomViewPagerActivity](https://github.com/qiangxi/PopularEffect/blob/master/app/src/main/java/com/qiangxi/populareffect/activity/viewpager/CustomViewPagerActivity.java)