package com.bd.gitlab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bd.gitlab.model.User;
import com.bd.gitlab.tools.Repository;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lenovo on 2016/7/14.
 */
public class ProfileActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Repository.getService().getUser(userCallback);
    }

    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void success(User user, Response response) {
            Log.v("=======","========"+user.getEmail());
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
}
