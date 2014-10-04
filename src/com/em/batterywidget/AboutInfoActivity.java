/*
 *  Copyright 2012 Erkan Molla
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.em.batterywidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.about_view);
        findViewById(R.id.paypal_button).setOnClickListener(lPaypal);
        findViewById(R.id.github_button).setOnClickListener(lGithub);
    }

    final private OnClickListener lPaypal = new OnClickListener() {
        public void onClick(View v) {
            Uri Url = Uri.parse(Constants.PaypalUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, Url));
        }
    };

    final private OnClickListener lGithub = new OnClickListener() {
        public void onClick(View v) {
            Uri Url = Uri.parse(Constants.GithubUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, Url));
        }
    };

}
