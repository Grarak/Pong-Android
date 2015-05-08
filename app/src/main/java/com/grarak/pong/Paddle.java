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

/**
 * Created by willi on 08.05.15.
 */
public class Paddle extends DrawObject {

    private final UIParent.PLAYER player;
    private final Paint paint;

    private Float height;
    private float length;
    private float thickness;

    public Paddle(Context context, UIParent.PLAYER player) {
        super(context);
        this.player = player;

        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas) {
        if (height == null) height = getDisplayHeight() / 2;
        length = getDisplayHeight() / 4;
        thickness = getDisplayWidth() / 80;

        float w = 0;
        if (height > getDisplayHeight() / 2) {
            if (height + length / 2 > getDisplayHeight()) height = getDisplayHeight() - length / 2;
        } else {
            if (height - length / 2 < 0) height = length / 2;
        }
        float h = height - length / 2;
        switch (player) {
            case ONE:
                w = getDisplayWidth() / 13;
                break;
            case TWO:
                w = getDisplayWidth() - getDisplayWidth() / 13 - thickness;
                break;
        }

        canvas.drawRect(w, h, thickness + w, length + h, paint);
    }

    public void move(float height) {
        this.height = height;
    }

    public void reset() {
        height = null;
    }

    public float getHeight() {
        return height - length / 2;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return thickness + getDisplayWidth() / 13;
    }

    public float getThickness() {
        return thickness;
    }

}
