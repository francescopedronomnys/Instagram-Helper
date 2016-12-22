package com.steelkiwi.exampleinstagramhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.steelkiwi.instagramhelper.model.InstagramUser;

import static android.app.Activity.RESULT_OK;

/**
 * Created by francesco on 22/12/16.
 */

public class MainFragment extends Fragment {
    private InstagramHelper instagramHelper;
    private LinearLayout userInfoPanel;
    private ImageView userPhoto;
    private TextView userTextInfo;
    private Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_layout, container, false);

        instagramHelper = TestApplication.getInstagramHelper();

        userInfoPanel = (LinearLayout) root.findViewById(R.id.user_instagram_info);
        userPhoto = (ImageView) root.findViewById(R.id.user_photo);
        userTextInfo = (TextView) root.findViewById(R.id.user_text_info);
        loginBtn = (Button) root.findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagramHelper.loginFromFragment(MainFragment.this);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
            InstagramUser user = instagramHelper.getInstagramUser(getActivity());
            loginBtn.setVisibility(View.GONE);
            userInfoPanel.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(user.getData().getProfilePicture()).into(userPhoto);
            userTextInfo.setText(user.getData().getUsername() + "\n"
                    + user.getData().getFullName() + "\n"
                    + user.getData().getWebsite()
            );

        } else {
            Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
        }
    }
}
