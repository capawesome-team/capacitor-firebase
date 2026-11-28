package io.capawesome.capacitorjs.plugins.firebase.messaging.classes.results;

import android.app.NotificationChannel;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.firebase.messaging.FirebaseMessagingHelper;
import io.capawesome.capacitorjs.plugins.firebase.messaging.interfaces.Result;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListChannelsResult implements Result {

    @NonNull
    private final List<NotificationChannel> channels;

    public ListChannelsResult(@NonNull List<NotificationChannel> channels) {
        this.channels = channels;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray channelsResult = new JSArray();
        for (NotificationChannel channel : channels) {
            JSObject channelResult = FirebaseMessagingHelper.createChannelResult(channel);
            channelsResult.put(channelResult);
        }

        JSObject result = new JSObject();
        result.put("channels", channelsResult);
        return result;
    }
}
