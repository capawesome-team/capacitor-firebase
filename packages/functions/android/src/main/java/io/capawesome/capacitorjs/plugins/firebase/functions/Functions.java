package io.capawesome.capacitorjs.plugins.firebase.functions;

import android.util.Log;

public class Functions {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
