package com.example.pypayqr;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import net.frakbot.jumpingbeans.JumpingBeans;

import android.content.SharedPreferences;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;



/**
 * A simple {@link Fragment} subclass.
 */
public class logincon extends Fragment {

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");


    public logincon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logincon, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView con;
        con= getView().findViewById(R.id.loginon);
        JumpingBeans jumpingBeans2 = JumpingBeans.with(con)
                .makeTextJump(0, con.getText().length())
                .setIsWave(true)
                .setLoopDuration(1500)
                .build();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String username =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");
        final String password =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("password", "");
        final NavController controller = Navigation.findNavController(view);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result_json = null;

                        OkHttpClient okHttpClient = new OkHttpClient();
                        JSONObject postData = new JSONObject();

                        try {
                            postData.put("user", username);
                            postData.put("pass",password);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Send error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        System.out.println(postData);

                        RequestBody body = RequestBody.create(MEDIA_TYPE, postData.toString());
                        System.out.println("poststring:"+body);

                        final Request request = new Request.Builder()
                                .url("https://us-central1-pypay-cloudserver.cloudfunctions.net/payment/androidauth")
                                .post(body)
                                .addHeader("Content-Type", "application/json")
                                .build();

                        Call call = okHttpClient.newCall(request);
                        Response response = call.execute();
                        if (response.isSuccessful()) {

                            pref.edit()
                                    .putString("ID",response.body().string())
                                    .apply();

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Login successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            controller.navigate(R.id.action_logincon_to_connect);

                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Please fill in the correct username and password!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            controller.navigate(R.id.action_logincon_to_login);
                        }
                    } catch (IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Connect error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                        controller.navigate(R.id.action_logincon_to_login);
                    }
                }
            }).start();
        }

    }


