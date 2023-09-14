package io.capawesome.capacitorjs.plugins.firebase.firestore;

import android.util.Log;

public class FirebaseFirestore {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
