package io.capawesome.capacitorjs.plugins.firebase.authentication.handlers;

import static org.junit.Assert.assertEquals;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import org.junit.Test;

public class GoogleAuthProviderHandlerTest {

    @Test
    public void createSignInWithGoogleOptionBuilderUsesButtonFlow() {
        GetSignInWithGoogleOption.Builder builder = GoogleAuthProviderHandler.createSignInWithGoogleOptionBuilder("server-client-id");

        assertEquals(GetSignInWithGoogleOption.Builder.class, builder.getClass());
    }
}
