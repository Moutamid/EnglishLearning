package app.com.englishlearning;

import android.app.Application;

import app.com.englishlearning.utilities.Utils;


public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
