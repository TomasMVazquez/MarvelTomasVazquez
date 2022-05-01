package com.toms.applications.marveltomasvazquez.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.ItemInfoStateBinding

enum class InfoState {
    NETWORK_ERROR,
    FAV_EMPTY_STATE,
    SEARCH_EMPTY,
    OTHER
}

class ItemInfoStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ItemInfoStateBinding

    init {
        ItemInfoStateBinding.inflate(LayoutInflater.from(context), this, true).also {
            binding = it
        }
    }

    fun setInfoState(infoState: InfoState) {
        with(binding) {
            when (infoState) {
                InfoState.NETWORK_ERROR -> {
                    stateImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_conection_error
                        )
                    )
                    stateText.text = context.getString(R.string.conection_error)
                }
                InfoState.FAV_EMPTY_STATE -> {
                    stateImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_ops_state
                        )
                    )
                    stateText.text = context.getString(R.string.empty_state)
                }
                InfoState.OTHER -> {
                    stateImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_ops_state
                        )
                    )
                    stateText.text = context.getString(R.string.other)
                }
                InfoState.SEARCH_EMPTY -> {
                    stateImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_ops_state
                        )
                    )
                    stateText.text = context.getString(R.string.search_emtpy)
                }
            }
        }
    }

}