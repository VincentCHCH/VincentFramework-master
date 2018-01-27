package com.vincent.network.navigator;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Vincent on 24/1/2018.
 */

public class Navigator {
    public Navigator() {

    }

    public void navigateToAbout(Context context ,Class mClass) {
        if (context != null) {
            Intent intent = new Intent(context, mClass);
            context.startActivity(intent);
        }
    }
    public void navigateToMyReact(Context context,Class mClass) {
        if (context != null) {
            Intent intent = new Intent(context,mClass);
            context.startActivity(intent);
        }
    }
}
