package com.toms.applications.marveltomasvazquez.ui.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.toms.applications.marveltomasvazquez.R

/**
 *  Allows to automatically adjust img
 */

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var ratio = 1f

    init {
        val styledAttribute =
            context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
        ratio = styledAttribute.getFloat(R.styleable.AspectRatioImageView_ratio, 1f)
        styledAttribute.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) return

        if (width > 0) {
            height = (width * ratio).toInt()
        } else if (height > 0) {
            width = (height * ratio).toInt()
        }

        setMeasuredDimension(width, height)
    }

}