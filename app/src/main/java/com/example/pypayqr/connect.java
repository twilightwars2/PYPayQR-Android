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
public class connect extends Fragment {

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");


    public connect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView con;
        con= getView().findViewById(R.id.connect);
        JumpingBeans jumpingBeans2 = JumpingBeans.with(con)
                .makeTextJump(0, con.getText().length())
                .setIsWave(true)
                .setLoopDuration(1500)
                .build();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final String userid =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("ID", "");
        final NavController controller = Navigation.findNavController(view);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);


        if(!userid.equals(""))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result_json = null;

                        OkHttpClient okHttpClient = new OkHttpClient();
                        JSONObject postData = new JSONObject();

                        try {
                            postData.put("user", userid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(postData);

                        RequestBody body = RequestBody.create(MEDIA_TYPE, postData.toString());
                        System.out.println("poststring:"+body);

                        final Request request = new Request.Builder()
                                .url("https://us-central1-pypay-cloudserver.cloudfunctions.net/payment/androidchk")
                                .post(body)
                                .addHeader("Content-Type", "application/json")
                                .build();

                        Call call = okHttpClient.newCall(request);
                        Response response = call.execute();
                        if (response.isSuccessful()) {


                            pref.edit()
                                    .putString("firstname", response.body().string())
                                    .apply();

                            controller.navigate(R.id.action_connect_to_QR);

                        }else controller.navigate(R.id.action_connect_to_login);
                    } catch (IOException e) {
                        e.printStackTrace();
                        controller.navigate(R.id.action_connect_to_login);
                    }
                }
            }).start();
        }
        else{
            controller.navigate(R.id.action_connect_to_login);
        }
    }

}
