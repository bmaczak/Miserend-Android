package com.frama.miserend.hu.utils

import android.content.Context
import android.content.res.TypedArray
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.frama.miserend.hu.R
import com.frama.miserend.hu.database.miserend.entities.Mass
import com.google.android.flexbox.FlexboxLayout

fun Context.getToolbarHeight(): Int {
    val styledAttributes = this.theme.obtainStyledAttributes(
            intArrayOf(R.attr.actionBarSize))
    val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
    styledAttributes.recycle()

    return toolbarHeight
}
/**
 * Created by Balazs on 2018. 02. 14..
 */

object ViewUtils {


    fun TextView.setTextOrHide(text: String?) {
        if (text != null && text.isNotEmpty()) {
            this.text = text
        } else {
            this.visibility = View.GONE
        }
    }

    fun TextView.setHtmlTextOrHide(text: String?) {
        if (text != null && text.isNotEmpty()) {
            this.text = Html.fromHtml(text)
        } else {
            this.visibility = View.GONE
        }
    }

    fun createMassFlexboxItem(layoutInflater: LayoutInflater, parent: FlexboxLayout, mass: Mass): View {
        val view = layoutInflater.inflate(R.layout.item_mass_flexbox_element, parent, false)
        (view.findViewById<View>(R.id.mass_time) as TextView).text = DateUtils.cutSecondsFromTime(mass.time)
        view.findViewById<View>(R.id.mass_info_icon).visibility = if (mass.hasInfo()) View.VISIBLE else View.GONE
        return view
    }

}
