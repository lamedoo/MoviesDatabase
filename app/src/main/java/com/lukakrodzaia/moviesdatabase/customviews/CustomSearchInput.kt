package com.lukakrodzaia.moviesdatabase.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import com.lukakrodzaia.moviesdatabase.utils.hideKeyboard
import com.lukakrodzaia.moviesdatabase.utils.showKeyboard

class CustomSearchInput
@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attributeSet, defStyle) {

    init {
        setTextChangeListener()
        setOnEditorActionListener()
        imeOptions = EditorInfo.IME_ACTION_SEARCH
    }

    private var queryTextListener: QueryTextListener? = null

    private fun setTextChangeListener() {
        doAfterTextChanged {
            queryTextListener?.onQueryTextChange(it.toString())
        }
    }

    private fun setOnEditorActionListener() {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                queryTextListener?.onQueryTextSubmit(text.toString())
                this.hideKeyboard()
                this.clearFocus()
                true
            } else {
                false
            }
        }
    }

    fun setQueryTextChangeListener(queryTextListener: QueryTextListener) {
        this.queryTextListener = queryTextListener
    }

    interface QueryTextListener {

        fun onQueryTextSubmit(query: String?)

        fun onQueryTextChange(newText: String?)

    }

}