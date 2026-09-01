package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import static org.junit.Assert.assertEquals;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import org.junit.Test;

public class GoogleAuthProviderHandlerTest {

    @Test
    public void createSignInWithGoogleOptionBuilderUsesButtonFlow() {
        String serverClientId = "1234567890-abcdefg.apps.googleusercontent.com";

        GetSignInWithGoogleOption option = GoogleAuthProviderHandler.createSignInWithGoogleOptionBuilder(serverClientId).build();

        assertEquals(serverClientId, option.getServerClientId());
    }
}
