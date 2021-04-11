package com.toms.applications.marveltomasvazquez.utils

import android.text.Editable
import android.text.InputFilter
import androidx.core.text.toSpannable

class MockEditable(private val str: String) : Editable {

    override fun get(index: Int): Char = str[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = str.subSequence(startIndex,endIndex)

    override fun getChars(p0: Int, p1: Int, p2: CharArray?, p3: Int){
        if (p2 != null) str.toCharArray(p2,p3,p0,p1)
    }

    override fun <T : Any?> getSpans(p0: Int, p1: Int, p2: Class<T>?): Array<T> = str.toSpannable().getSpans(p0,p1,p2)

    override fun getSpanStart(p0: Any?): Int = str.toSpannable().getSpanStart(p0)

    override fun getSpanEnd(p0: Any?): Int = str.toSpannable().getSpanEnd(p0)

    override fun getSpanFlags(p0: Any?): Int = str.toSpannable().getSpanFlags(p0)

    override fun nextSpanTransition(p0: Int, p1: Int, p2: Class<*>?): Int = str.toSpannable().nextSpanTransition(p0, p1, p2)

    override fun setSpan(p0: Any?, p1: Int, p2: Int, p3: Int) = str.toSpannable().setSpan(p0, p1, p2, p3)

    override fun removeSpan(p0: Any?) = str.toSpannable().removeSpan(p0)

    override fun append(p0: CharSequence?): Editable = this

    override fun append(p0: CharSequence?, p1: Int, p2: Int): Editable = this

    override fun append(p0: Char): Editable = this

    override fun replace(p0: Int, p1: Int, p2: CharSequence?, p3: Int, p4: Int): Editable = this

    override fun replace(p0: Int, p1: Int, p2: CharSequence?): Editable = this

    override fun insert(p0: Int, p1: CharSequence?, p2: Int, p3: Int): Editable = this

    override fun insert(p0: Int, p1: CharSequence?): Editable = this

    override fun delete(p0: Int, p1: Int): Editable = this

    override fun clear() {}

    override fun clearSpans() {}

    override fun setFilters(p0: Array<out InputFilter>?) {}

    override fun getFilters(): Array<InputFilter> = emptyArray()

    override val length: Int = str.length
}