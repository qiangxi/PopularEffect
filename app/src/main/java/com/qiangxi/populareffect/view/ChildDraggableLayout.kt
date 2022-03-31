package com.duowan.mobile.drag

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper

/**
 * Create By renqiangqiang . 2022/3/21
 * 子view可随意拖动的ViewGroup
 * - 支持多个子view拖动
 * - 已处理好子view的layout
 * - 已处理好padding和子view的margin
 * - 支持两种拖动模式
 */
abstract class ChildDraggableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * 子view被Captured时的初始left
     */
    protected var mInitDragLeft = 0

    /**
     * 子view被Captured时的初始top
     */
    protected var mInitDragTop = 0

    /**
     * 被拖拽的子view实时的left
     */
    protected var mCurrentDragLeft = 0

    /**
     * 被拖拽的子view实时的top
     */
    protected var mCurrentDragTop = 0

    /**
     * 拖拽状态监听
     */
    private var mDragStateChangedListener: DragStateChangedListener? = null

    /**
     * 被拖拽的view
     */
    protected var mDraggedView: View? = null

    /**
     * 当前Orientation
     */
    protected var mCurrentOrientation = context.resources.configuration.orientation

    /**
     * 当前拖拽模式
     */
    protected var mCurrentDragMode = DragMode.STABLE

    /**
     * 拖拽限制区域
     */
    protected var mDragLimitBound = Rect()

    private val mViewDragCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            // 外部没设置限制区域，就默认为父布局区域
            if (mDragLimitBound.isEmpty) {
                mDragLimitBound.set(left + paddingLeft + capturedChild.marginLeft,
                    top + paddingTop + capturedChild.marginTop,
                    right - paddingRight - capturedChild.marginRight,
                    bottom - paddingBottom - capturedChild.marginBottom)
            }
            mInitDragLeft = capturedChild.left
            mInitDragTop = capturedChild.top
            val lp = (capturedChild.layoutParams as DragLayoutParams)
            lp.mDragLeft = mInitDragLeft
            lp.mDragTop = mInitDragTop
            mDraggedView = capturedChild
            mDragStateChangedListener?.onDragStart(capturedChild)
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            mDragStateChangedListener?.onDragStop(releasedChild)
            onDragRelease(releasedChild, xvel, yvel)
            mDragLimitBound.setEmpty()
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (state == ViewDragHelper.STATE_DRAGGING) {
                mDragStateChangedListener?.onDragging(mDraggedView)
            }
        }

        override fun clampViewPositionHorizontal(child: View, childLeft: Int, dx: Int): Int {
            mCurrentDragLeft = if (mCurrentDragMode == DragMode.OVERFLOW) {
                childLeft
            } else {
                val rightLimit = mDragLimitBound.right - child.width
                val leftLimit = mDragLimitBound.left
                when {
                    childLeft < leftLimit -> {
                        leftLimit
                    }
                    childLeft > rightLimit -> {
                        rightLimit
                    }
                    else -> {
                        childLeft
                    }
                }
            }
            return mCurrentDragLeft
        }

        override fun clampViewPositionVertical(child: View, childTop: Int, dy: Int): Int {
            mCurrentDragTop = if (mCurrentDragMode == DragMode.OVERFLOW) {
                childTop
            } else {
                val bottomLimit = mDragLimitBound.bottom - child.height
                val topLimit = mDragLimitBound.top
                when {
                    childTop < topLimit -> {
                        topLimit
                    }
                    childTop > bottomLimit -> {
                        bottomLimit
                    }
                    else -> {
                        childTop
                    }
                }
            }
            return mCurrentDragTop
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return measuredWidth - child.measuredWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return measuredHeight - child.measuredHeight
        }
    }

    protected val mViewDragHelper: ViewDragHelper = ViewDragHelper.create(this, 0.75F, mViewDragCallback)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) return false
        if (ev.action == MotionEvent.ACTION_DOWN
            && mViewDragHelper.findTopChildUnder(ev.x.toInt(), ev.y.toInt()) == null) {
            return false
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    /**
     * 拖拽释放时调用
     */
    abstract fun onDragRelease(child: View, xvel: Float, yvel: Float)

    /**
     * 设置拖拽监听
     */
    fun setOnDragStateChangedListener(listener: DragStateChangedListener?) {
        mDragStateChangedListener = listener
    }

    /**
     * 设置拖拽模式[DragMode]
     */
    fun setDragMode(dragMode: DragMode) {
        mCurrentDragMode = dragMode
    }

    /**
     * 设置拖拽限制区域
     */
    fun setDragLimitBound(rect: Rect) {
        mDragLimitBound.set(rect)
    }

    /**
     * view 拖拽状态监听
     */
    interface DragStateChangedListener {
        /**
         * 开始拖动
         */
        fun onDragStart(draggedView: View?)

        /**
         * 正在拖动
         */
        fun onDragging(draggedView: View?)

        /**
         * 结束拖动
         */
        fun onDragStop(draggedView: View?)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (mDraggedView == null) {
            super.onLayout(changed, l, t, r, b)
            return
        }
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child != null && child.visibility != GONE) {
                val st = child.layoutParams as DragLayoutParams
                child.layout(st.mDragLeft, st.mDragTop, child.measuredWidth + st.mDragLeft,
                    child.measuredHeight + st.mDragTop)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val orientation = newConfig.orientation
        if (orientation != mCurrentOrientation) {
            mCurrentOrientation = orientation
            // 重置为空的目的：切横竖屏时，回归默认位置
            mDraggedView = null
        }
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return DragLayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return DragLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return when (lp) {
            is DragLayoutParams -> DragLayoutParams(lp)
            is MarginLayoutParams -> DragLayoutParams(lp)
            else -> DragLayoutParams(lp)
        }
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is DragLayoutParams
    }

    class DragLayoutParams : LayoutParams {
        internal var mDragLeft: Int = 0
        internal var mDragTop: Int = 0

        constructor(width: Int, height: Int) : super(width, height)
        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs)
        constructor(source: ViewGroup.LayoutParams?) : super(source)
        constructor(source: MarginLayoutParams?) : super(source)
        constructor(source: LayoutParams?) : super(source)
    }

    enum class DragMode {
        /**
         * 溢出模式，该模式下，子view可以拖出限制区域，在整个parent区域进行拖拽，但松手时，会回弹到最近的限制区域边缘
         * - 回弹效果由[EdgeStickDraggableLayout]子类实现
         */
        OVERFLOW,

        /**
         * 稳定模式，该模式下，子view只能在限制区域内拖动，不能超出限制区域
         */
        STABLE
    }

    protected inline val View.marginLeft: Int
        get() = (layoutParams as? MarginLayoutParams)?.leftMargin ?: 0

    protected inline val View.marginTop: Int
        get() = (layoutParams as? MarginLayoutParams)?.topMargin ?: 0

    protected inline val View.marginRight: Int
        get() = (layoutParams as? MarginLayoutParams)?.rightMargin ?: 0

    protected inline val View.marginBottom: Int
        get() = (layoutParams as? MarginLayoutParams)?.bottomMargin ?: 0
}