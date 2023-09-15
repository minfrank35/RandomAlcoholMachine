package org.techtown.cap2;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected void finishApp() {
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }
}
