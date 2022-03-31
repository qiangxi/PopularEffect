package com.duowan.mobile.drag

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Create By renqiangqiang . 2022/3/21
 * 可随意拖拽子view，抬手时，子view可吸附贴边（磁吸效果）
 */
class EdgeStickDraggableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ChildDraggableLayout(context, attrs, defStyleAttr) {

    /**
     * 拖拽释放时调用
     */
    override fun onDragRelease(child: View, xvel: Float, yvel: Float) {
        val finalLeft = if (mCurrentDragLeft <= (mDragLimitBound.right + mDragLimitBound.left) / 2) {
            mDragLimitBound.left
        } else {
            mDragLimitBound.right - child.width
        }
        val finalTop = if (mCurrentDragMode == DragMode.OVERFLOW) {
            when {
                mCurrentDragTop < mDragLimitBound.top -> { // 顶部超过上限
                    mDragLimitBound.top
                }
                (mCurrentDragTop + child.height) >= mDragLimitBound.bottom -> { // 底部超过下限
                    mDragLimitBound.bottom - child.height
                }
                else -> { // 在限制之间
                    mCurrentDragTop
                }
            }
        } else {
            mCurrentDragTop
        }
        val lp = (child.layoutParams as DragLayoutParams)
        lp.mDragLeft = finalLeft
        lp.mDragTop = finalTop
        mViewDragHelper.settleCapturedViewAt(finalLeft, finalTop)
        invalidate()
    }
}