package com.qiangxi.populareffect.utli;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * 获取屏幕宽高,像素值,px/dp/sp之间的相互转换,屏幕截图等
 *
 * @author 任强强
 *         创建于2016/1/5 20:12
 */
public class ScreenUtils {
    /**
     * 获取屏幕宽高时用，屏幕宽度的单位为px
     */
    public static final int TYPE_PX = 0;
    /**
     * 获取屏幕宽高时用,屏幕宽度的单位为dp
     */
    public static final int TYPE_DP = 1;
    /**
     * 获取当前屏幕截图时用,截图包含状态栏
     */
    public static final int TYPE_HAVE_STATUS_BAR = 2;
    /**
     * 获取当前屏幕截图时用,截图不包含状态栏
     */
    public static final int TYPE_NO_STATUS_BAR = 3;

    private ScreenUtils() {
    }

    /**
     * dp转px
     */
    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float pxToDp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale);
    }

    /**
     * sp转px
     */
    public static int spToPx(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static float pxToSp(Context context, int px) {
        return (px / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     *
     * @param type 指定type为TYPE_DP,返回单位为dp的屏幕宽度,指定type为TYPE_PX,返回单位为px的屏幕宽度,填写其他值则抛出异常
     * @return 返回当前屏幕宽度
     */
    public static int getScreenWidth(Context context, int type) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        if (type == TYPE_DP) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (metrics.widthPixels / scale);
        }
        else if (type == TYPE_PX) {
            return metrics.widthPixels;
        }
        else {
            throw new IllegalArgumentException("type的值只能是TYPE_DP或TYPE_PX");
        }

    }

    /**
     * 获取屏幕高度
     *
     * @param type 指定type为TYPE_DP,返回单位为dp的屏幕高度,指定type为TYPE_PX,返回单位为px的屏幕高度,填写其他值则抛出异常
     * @return 返回当前屏幕高度
     */
    public static int getScreenHeight(Context context, int type) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        if (type == TYPE_DP) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (metrics.heightPixels / scale);
        }
        else if (type == TYPE_PX) {
            return metrics.heightPixels;
        }
        else {
            throw new IllegalArgumentException("type的值只能是TYPE_DP或TYPE_PX");
        }
    }

    /**
     * 获取当前屏幕截图
     *
     * @param activity 上下文的Activity
     * @param type     type为TYPE_NO_STATUS_BAR：截图不包含状态栏，type为TYPE_HAVE_STATUS_BAR：截图包含状态栏
     * @return 返回当前的屏幕截图
     */
    public static Bitmap getScreenShot(Activity activity, int type) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity, TYPE_PX);
        int height = getScreenHeight(activity, TYPE_PX);
        Bitmap bitmap;
        if (type == TYPE_HAVE_STATUS_BAR) {
            bitmap = Bitmap.createBitmap(bmp, 0, 0, width, height);
            view.destroyDrawingCache();
            return bitmap;
        }
        else if (type == TYPE_NO_STATUS_BAR) {
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            bitmap = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                    - statusBarHeight);
            view.destroyDrawingCache();
            return bitmap;
        }
        else {
            throw new IllegalArgumentException("type的值只能是TYPE_NO_STATUS_BAR或TYPE_HAVE_STATUS_BAR");
        }
    }
}
