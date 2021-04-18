package com.app.batteryalarmgold.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.app.batteryalarmgold.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

/**
 * A custom [IDCardView] to the [_icon], [_title]
 * and [_description] in a fancy style.
 *
 * @author Lokik Soni
 * Created on 28-02-2021
 **/
class IDCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle,
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val imgIDIcon: ShapeableImageView
    private val txtIDTitle: MaterialTextView
    private val txtIDDescription: MaterialTextView

    private var _icon: Drawable? = null
        set(value) {
            value?.let {
                imgIDIcon.setImageDrawable(value)
                imgIDIcon.visibility = View.VISIBLE

            } ?: kotlin.run {
                imgIDIcon.visibility = View.GONE
            }

            field = value
        }

    private var _iconBackgroundColor: Drawable? = null
        set(value) {
            imgIDIcon.background = value
            field = value
        }

    private var _title: String = ""
        set(value) {
            txtIDTitle.text = value
            field = value
        }

    private var _description: String = ""
        set(value) {
            txtIDDescription.text = value
            field = value
        }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.id_cardview_layout, this, true)
        imgIDIcon = view.findViewById(R.id.img_id_card_icon)
        txtIDTitle = view.findViewById(R.id.txt_id_card_title)
        txtIDDescription = view.findViewById(R.id.txt_id_card_description)

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.IDCardView,
            defStyleAttr,
            0
        )

        _icon = a.getDrawable(R.styleable.IDCardView_idIcon)
        _iconBackgroundColor = a.getDrawable(R.styleable.IDCardView_idIconBackgroundColor)
        _title = a.getString(R.styleable.IDCardView_idTitle) ?: _title
        _description = a.getString(R.styleable.IDCardView_idDescription) ?: _description

        preventCornerOverlap = false
        imgIDIcon.shapeAppearanceModel = shapeAppearanceModel

        a.recycle()
    }

    fun setIdTitle(title: String) {
        _title = title
    }

    fun setIdDescription(description: String) {
        _description = description
    }
}