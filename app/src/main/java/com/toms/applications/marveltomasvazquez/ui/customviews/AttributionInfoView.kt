package com.toms.applications.marveltomasvazquez.ui.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.R

/**
 * Allows to create a view wit all information needed and reduce XML size
 */
class AttributionInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setAttributions(character: MyCharacter) {
        text = buildSpannedString {
            if (character.description.isNotEmpty()) {
                bold { append(context.getString(R.string.detail_description)) }
                appendLine(" ${character.description}")
                appendLine()
            }
            bold { append(context.getString(R.string.detail_comics)) }
            appendLine(" ${character.comics}")
            appendLine()
            bold { append(context.getString(R.string.detail_series)) }
            appendLine(" ${character.series}")
            appendLine()
            bold { append(context.getString(R.string.detail_stories)) }
            appendLine(" ${character.stories}")
            appendLine()
            bold { append(context.getString(R.string.detail_events)) }
            appendLine(" ${character.events}")
            appendLine()
        }
    }

}