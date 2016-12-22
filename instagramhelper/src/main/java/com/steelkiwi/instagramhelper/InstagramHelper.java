package com.steelkiwi.instagramhelper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.steelkiwi.instagramhelper.model.InstagramUser;
import com.steelkiwi.instagramhelper.utils.SharedPrefUtils;

import java.text.MessageFormat;

import static com.steelkiwi.instagramhelper.InstagramHelperConstants.AUTH_URL;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.CLIENT_ID_DEF;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.INSTA_AUTH_URL;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.INSTA_LOGIN;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.INSTA_REDIRECT_URL;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.REDIRECT_URI_DEF;
import static com.steelkiwi.instagramhelper.InstagramHelperConstants.RESPONSE_TYPE_DEF;
import static com.steelkiwi.instagramhelper.utils.CommonUtils.checkNotNull;

public class InstagramHelper {

    private String clientId;
    private String redirectUri;
    private String scope;

    private InstagramHelper(String clientId, String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scope = scope;
    }

    public void loginFromActivity(Activity context) {
        context.startActivityForResult(buildIntent(context), INSTA_LOGIN);
    }

    public void loginFromFragment(Fragment fragment) {
        fragment.startActivityForResult(buildIntent(fragment.getActivity()), INSTA_LOGIN);
    }

    public void loginFromFragment(android.support.v4.app.Fragment fragment) {
        fragment.startActivityForResult(buildIntent(fragment.getActivity()), INSTA_LOGIN);
    }

    private Intent buildIntent(Context context) {
        String authUrl = MessageFormat.format(AUTH_URL + CLIENT_ID_DEF + "{0}" + REDIRECT_URI_DEF + "{1}" + RESPONSE_TYPE_DEF, clientId, redirectUri);
        Intent intent = new Intent(context, InstagramLoginActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(INSTA_AUTH_URL, authUrl);
        bundle.putString(INSTA_REDIRECT_URL, redirectUri);
        intent.putExtras(bundle);
        return intent;
    }

    public InstagramUser getInstagramUser(Context context) {
        return SharedPrefUtils.getInstagramUser(context);
    }

    public static final class Builder {
        private String clientId;
        private String redirectUrl;
        private String scope;

        public Builder withClientId(String clientId) {
            this.clientId = checkNotNull(clientId, "clientId == null");
            return this;
        }

        public Builder withRedirectUrl(String redirectUrl) {
            this.redirectUrl = checkNotNull(redirectUrl, "redirectUrl == null");
            return this;
        }

        public Builder withScope(String scope) {
            this.scope = checkNotNull(scope, "scope == null");
            return this;
        }

        public InstagramHelper build() {
            return new InstagramHelper(clientId, redirectUrl);
        }
    }
}
