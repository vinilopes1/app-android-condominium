package com.example.vinicius.condominium.utils

import android.content.Context
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.graphics.drawable.VectorDrawableCompat


object VectorDrawableUtils {

    fun getDrawable(context: Context, drawableResId: Int): Drawable? {
        return VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme())
    }

    fun getDrawable(context: Context, drawableResId: Int, colorFilter: Int): Drawable {
        val drawable = getDrawable(context, drawableResId)
        drawable!!.setColorFilter(ContextCompat.getColor(context, colorFilter), PorterDuff.Mode.SRC_IN)
        return drawable
    }

    fun getBitmap(context: Context, drawableId: Int): Bitmap {
        val drawable = getDrawable(context, drawableId)

        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)

        return bitmap
    }
}