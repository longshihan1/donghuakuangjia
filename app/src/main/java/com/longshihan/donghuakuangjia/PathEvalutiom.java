package com.longshihan.donghuakuangjia;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author Administrator
 * @time 2016-10-26 0026 下午 09:50
 * @des 估值器
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class PathEvalutiom implements TypeEvaluator<PointF> {
    private PointF mPointFB;
    private PointF mPointFC;

    public PathEvalutiom(PointF pointFC, PointF pointFB) {
        mPointFC = pointFC;
        mPointFB = pointFB;
    }

    /**
     * @param v      百分比
     * @param pointF 起点
     * @param t1     终点
     * @return
     */
    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        float x, y;
        float onMinust = 1 - v;
        x = onMinust * onMinust * onMinust * pointF.x +
                3 * v * onMinust * onMinust * mPointFB.x +
                3 * v * v * onMinust * mPointFC.x +
                v * v * v * t1.x;

        y = onMinust * onMinust * onMinust * pointF.y +
                3 * v * onMinust * onMinust * mPointFB.y +
                3 * v * v * onMinust * mPointFC.y +
                v * v * v * t1.y;
        return new PointF(x,y);
    }
}
