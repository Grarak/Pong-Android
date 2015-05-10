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
public class Ball extends DrawObject {

    private final Paddle paddleOne;
    private final Paddle paddleTwo;
    private final Paint paint;
    private final OnPointListener onPointListener;

    private Vector position;
    private Vector velocity;

    private boolean gameover;
    private boolean reset;

    public Ball(Context context, Paddle paddleOne, Paddle paddleTwo, OnPointListener onPointListener) {
        super(context);
        this.paddleOne = paddleOne;
        this.paddleTwo = paddleTwo;
        paint = new Paint();
        paint.setColor(Color.RED);
        this.onPointListener = onPointListener;
    }

    @Override
    public void draw(Canvas canvas) {
        if (reset) return;
        if (position == null) position = new Vector(getDisplayWidth() / 2, getDisplayHeight() / 2);
        if (velocity == null) velocity = new Vector(-(getDisplayWidth() / 192), 0);
        float radius = 5 * getDensity();

        position.x += velocity.x;
        position.y += velocity.y;

        if (!gameover) {
            if (velocity.x < 0
                    && position.x - radius < paddleOne.getWidth()
                    && touchedPaddle(paddleOne)) {
                velocity.x *= -1;
                velocity.y += getVelocityY(paddleOne, radius);
                position.x = paddleOne.getWidth() + radius * 2;
            }

            if (velocity.x > 0
                    && position.x + radius > getDisplayWidth() - paddleTwo.getWidth()
                    && touchedPaddle(paddleTwo)) {
                velocity.x *= -1;
                velocity.y += getVelocityY(paddleTwo, radius);
                position.x = getDisplayWidth() - paddleTwo.getWidth();
            }

            if (touchedWall(getDisplayHeight(), radius)) velocity.y *= -1;
        }

        canvas.drawCircle(position.x - radius, position.y, radius, paint);
        if (gameover && (position.x + radius < 0 || position.x - radius > getDisplayWidth())) {
            reset = true;
            if (velocity.x < 0) onPointListener.playerTwo();
            else onPointListener.playerOne();
        }

        if (position.x - radius < paddleOne.getWidth() - paddleOne.getThickness() ||
                position.x + radius > getDisplayWidth() - paddleTwo.getWidth() + paddleTwo.getThickness())
            gameover = true;
    }

    public void reset() {
        position = null;
        velocity.y = 0;
        gameover = false;
        reset = false;
    }

    private boolean touchedWall(double wallHeight, float radius) {
        return position.y - radius <= 0 || position.y + radius >= wallHeight;
    }

    private boolean touchedPaddle(Paddle paddle) {
        return position.y > paddle.getHeight() && position.y < paddle.getHeight() + paddle.getLength();
    }

    private double getVelocityY(Paddle paddle, float radius) {
        Float[][] hitboxes = new Float[5][2];
        for (int i = 0; i < hitboxes.length; i++) {
            hitboxes[i][0] = (paddle.getLength() / hitboxes.length) * i + paddle.getHeight();
            hitboxes[i][1] = (paddle.getLength() / hitboxes.length) * (i + 1) + paddle.getHeight();
            if (i == 0) hitboxes[i][0] -= radius;
            else if (i == hitboxes.length - 1) hitboxes[i][1] += radius;
        }

        for (int i = 0; i < hitboxes.length; i++) {
            if (position.y > hitboxes[i][0] && position.y < hitboxes[i][1])
                switch (i) {
                    case 0:
                        return -4;
                    case 4:
                        return 4;
                    case 1:
                        return -2;
                    case 3:
                        return 2;
                }
        }
        return 0;
    }

    public interface OnPointListener {
        void playerOne();

        void playerTwo();
    }

}
