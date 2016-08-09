package com.bd.gitlab;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bd.gitlab.model.User;
import com.bd.gitlab.tools.Repository;
import com.bd.gitlab.views.CircleTransform;
import com.squareup.picasso.Picasso;

import java.nio.Buffer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lenovo on 2016/7/14.
 */
public class ProfileActivity extends Activity{
    @InjectView(R.id.avatar)
    ImageView mAvatar;
    @InjectView(R.id.nickname)
    EditText mNickname;
    @InjectView(R.id.email_text)
    EditText mEmail;
    @InjectView(R.id.public_email_text)
    EditText mPublicEmail;
    @InjectView(R.id.skype_text)
    EditText mSkype;
    @InjectView(R.id.linkidin_text)
    EditText mLinkedin;
    @InjectView(R.id.twitter_text)
    EditText mTwitter;
    @InjectView(R.id.website_text)
    EditText mWebsite;
    @InjectView(R.id.bio_text)
    EditText mBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        Repository.getService().getUser(userCallback);
    }

    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void success(User user, Response response) {
            Picasso.with(ProfileActivity.this).load(user.getAvatarUrl()).placeholder(R.drawable.default_avatar).transform(new CircleTransform()).into(mAvatar);
            if(!TextUtils.isEmpty(user.getName())){
                mNickname.setText(user.getName());
            }

            if(!TextUtils.isEmpty(user.getEmail())){
                mEmail.setText(user.getEmail());
            }

            if(!TextUtils.isEmpty(user.getSkype())){
                mSkype.setText(user.getSkype());
            }

            if(!TextUtils.isEmpty(user.getTwitter())){
                mTwitter.setText(user.getTwitter());
            }

            if(!TextUtils.isEmpty(user.getLinkedin())){
                mLinkedin.setText(user.getLinkedin());
            }

            if(!TextUtils.isEmpty(user.getWebsite_url())){
                mWebsite.setText(user.getWebsite_url());
            }

            if(!TextUtils.isEmpty(user.getBio())){
                mBio.setText(user.getBio());
            }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
}
