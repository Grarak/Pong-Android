/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grarak.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * Created by willi on 08.05.15.
 */
public class Point extends DrawObject {

    private final UIParent.PLAYER player;
    private final TextPaint textPaint;
    protected int point = 0;

    public Point(Context context, UIParent.PLAYER player) {
        super(context);
        this.player = player;

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(20 * getDensity());
    }

    @Override
    public void draw(Canvas canvas) {
        float w = 0;
        switch (player) {
            case ONE:
                w = getDisplayWidth() / 4;
                break;
            case TWO:
                w = getDisplayWidth() - getDisplayWidth() / 4;
                break;
        }

        float textHeight = textPaint.descent() - textPaint.ascent();
        float textOffset = (textHeight / 2) - textPaint.descent();
        canvas.drawText(String.valueOf(point), w, getDisplayHeight() / 20 + textOffset, textPaint);
    }
}
