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

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by willi on 08.05.15.
 */
public class MainActivity extends Activity {

    private int fps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        final UIParent uiParent = new UIParent(this);
        setContentView(uiParent);

        new Thread(() -> {
            while (true)
                try {
                    runOnUiThread(() -> {
                        uiParent.invalidate();
                        fps++;
                    });
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
        }).start();

        new Thread(() -> {
            while (true)
                try {
                    Thread.sleep(1000);
                    log("FPS: " + fps);
                    fps = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
        }).start();
    }

    public static void log(String message) {
        Log.i("Pong", message);
    }

}
