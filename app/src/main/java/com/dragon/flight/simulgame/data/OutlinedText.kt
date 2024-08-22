package com.dragon.flight.simulgame.data

import android.widget.TextView

data class OutlinedText(
    val tv1:TextView,
    val tv2:TextView,
    val tv3:TextView,
    val tv4:TextView,
    val shadowWidth:Float,
    val shadowColor:Int
){
    fun makeTVOutlined() {
        tv1.paint.setShadowLayer(shadowWidth, shadowWidth,0F, shadowColor)
        tv2.paint.setShadowLayer(shadowWidth, -shadowWidth,0F, shadowColor)
        tv3.paint.setShadowLayer(shadowWidth, 0F,shadowWidth, shadowColor)
        tv4.paint.setShadowLayer(shadowWidth, 0F,-shadowWidth, shadowColor)
    }
}
