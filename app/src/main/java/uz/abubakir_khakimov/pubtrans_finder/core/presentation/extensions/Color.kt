package uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int) =  ContextCompat.getColor(
    /* context = */ this,
    /* id = */ colorRes
)

@ColorInt
fun Fragment.getColorCompat(@ColorRes colorRes: Int) = requireActivity()
    .getColorCompat(colorRes = colorRes)

@ColorInt
fun Context.getColorCompatFromAttr(@AttrRes colorAttrRes: Int) = getColorCompat(
    colorRes = getResIdFromAttribute(attr = colorAttrRes)
)

@ColorInt
fun Fragment.getColorCompatFromAttr(@AttrRes colorAttrRes: Int) = requireActivity()
    .getColorCompatFromAttr(
        colorAttrRes = colorAttrRes
    )

fun Context.getResIdFromAttribute(@AttrRes attr: Int): Int {
    if (attr == 0) return 0

    val typedValue = TypedValue()
    theme.resolveAttribute(
        /* resid = */ attr,
        /* outValue = */ typedValue,
        /* resolveRefs = */ true
    )
    val resourceId = typedValue.resourceId

    return if (resourceId != 0) resourceId else typedValue.data
}

@ColorInt
fun Fragment.getColorFromStyle(styleResId: Int, attributeId: Int) = requireActivity()
    .getColorFromStyle(
        styleResId = styleResId,
        attributeId = attributeId
    )

@ColorInt
fun Context.getColorFromStyle(styleResId: Int, attributeId: Int): Int {
    val typedArray = theme.obtainStyledAttributes(
        /* resId = */ styleResId,
        /* attrs = */ intArrayOf(attributeId)
    )
    val color = typedArray.getColor(/* index = */ 0, /* defValue = */ 0)

    typedArray.recycle()
    return color
}

// auxiliary ->

fun getColorComStateList(context: Context, @ColorRes colorRes: Int): ColorStateList =
    ColorStateList.valueOf(/* color = */ context.getColorCompat(colorRes = colorRes))

fun CardView.setCardBackgroundColorCom(@ColorRes colorRes: Int) =
    setCardBackgroundColor(/* color = */ context.getColorCompat(colorRes = colorRes))

fun TextView.setTextColorCom(@ColorRes colorRes: Int) =
    setTextColor(/* color = */ context.getColorCompat(colorRes = colorRes))

fun TextView.setTextAttrColorCom(@AttrRes colorAttrRes: Int) =
    setTextColor(/* color = */ context.getColorCompatFromAttr(colorAttrRes = colorAttrRes))

fun Fragment.resetStatusBarColor() = setStatusBarColorAttrRes(
    colorAttrRes = android.R.attr.statusBarColor
)

fun Fragment.setStatusBarColorAttrRes(@AttrRes colorAttrRes: Int) = setStatusBarColor(
    color = getColorCompatFromAttr(colorAttrRes = colorAttrRes)
)

fun Fragment.setStatusBarColorRes(@ColorRes colorRes: Int) = setStatusBarColor(
    color = getColorCompat(colorRes = colorRes)
)

fun Fragment.setStatusBarColor(@ColorInt color: Int) {
    requireActivity().window?.statusBarColor = color
}