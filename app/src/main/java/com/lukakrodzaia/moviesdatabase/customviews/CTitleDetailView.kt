package com.lukakrodzaia.moviesdatabase.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.databinding.CustomTitleDetailItemBinding

class CTitleDetailView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: CustomTitleDetailItemBinding = CustomTitleDetailItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var info = ""
    private var name = ""

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTitleDetailView, 0,0).apply {
            try {
                info = getString(R.styleable.CustomTitleDetailView_detailInfo).toString()
                binding.detailInfo.text = info

                name = getString(R.styleable.CustomTitleDetailView_detailName).toString()
                binding.detailName.text = name
            } finally {
                recycle()
            }
        }
    }

    fun setDetailInfo(info: String) {
        binding.detailInfo.text = info
    }
}