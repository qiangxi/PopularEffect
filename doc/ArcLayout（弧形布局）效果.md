## ArcLayout（弧形布局）效果

### 难度
一般

### 效果说明
弧形布局，不知道的参见效果图。

该样例只实现底部凸起弧形布局，要往完善了做的话，可以增加各个方向的弧形，
毕竟原理都是二阶贝塞尔曲线。

特性：
- 已经做好了状态恢复与保存
- 可以配置弧形高度
- 可以配置弧形颜色
- 如果你愿意可以暴露更多属性用来配置

使用场景很多很多，比如美团主页（底部凹陷弧形），ofo主页（顶部凸起弧形）等

### 效果图
![效果图](https://github.com/qiangxi/PopularEffect/blob/master/gif/image001.png?raw=true)

### 实现原理
核心原理就是二阶贝塞尔曲线的使用；并且由于是继承自ViewGroup实现，
所以需要在`dispatchDraw(Canvas canvas)`方法中进行绘制。

该效果还有另一种实现方式，就是使用`canvas.drawArc()`的方式绘制弧形，效果基本也能达到，但是弧线的锯齿很严重，影响美观。

Github上还有一种实现方式，虽然也是采用贝塞尔曲线，但却不是绘制，而是使用`canvas.clipPath(Path clipPath)`方法进行裁剪，
即把指定方向上的布局裁剪成弧形，这种方式理论上很不错，但是问题就在于弧线锯齿状况非常严重，没法用在实际项目中；不过该项目实现了
弧线外围阴影效果，但是却是使用`ViewCompat.setElevation()`的方式实现的，使用该种方式实现阴影首先就是版本适配问题，在5.0以下
同样没有阴影效果，另外，该种方式不能设置阴影颜色及范围，所以感觉很鸡肋（注意哈，我是说`ViewCompat.setElevation()`方法很鸡肋，
不是该项目鸡肋）。该项目地址：[ArcLayout](https://github.com/florent37/ArcLayout)

### 核心代码
```java
 @Override
 protected void dispatchDraw(Canvas canvas) {
     super.dispatchDraw(canvas);
     mPaint.setColor(mArcColor);
     mPath.moveTo(0, getHeight());
     mPath.quadTo(getWidth() / 2, getHeight() - mArcHeight, getWidth(), getHeight());
     mPath.close();
     canvas.drawPath(mPath, mPaint);
 }
```

### 样例路径
[ArcLayout](https://github.com/qiangxi/PopularEffect/blob/master/app/src/main/java/com/qiangxi/populareffect/view/ArcLayout.java)

[ArcLayoutActivity](https://github.com/qiangxi/PopularEffect/blob/master/app/src/main/java/com/qiangxi/populareffect/activity/layout/ArcLayoutActivity.java)