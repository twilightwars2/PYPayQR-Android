package com.example.pypayqr;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import net.frakbot.jumpingbeans.JumpingBeans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Ref;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class signupcon extends Fragment {

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");


    public signupcon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signupcon, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView con;
        con= getView().findViewById(R.id.signon);
        JumpingBeans jumpingBeans2 = JumpingBeans.with(con)
                .makeTextJump(0, con.getText().length())
                .setIsWave(true)
                .setLoopDuration(1500)
                .build();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //http request

        final String username =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");
        final String password =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("password", "");
        final String email =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("email", "");
        final String fname =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("firstname", "");
        final String lname =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("lastname", "");

        final NavController controller = Navigation.findNavController(view);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);

        final int balance = 0;


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result_json = null;

                    OkHttpClient okHttpClient = new OkHttpClient();
                    JSONObject postData = new JSONObject();


                    try {
                        postData.put("balance",balance);
                        postData.put("email",email);
                        postData.put("fname",fname);
                        postData.put("lname",lname);
                        postData.put("pass",password);
                        postData.put("user", username);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Send error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    System.out.println(postData.toString());

                    RequestBody body = RequestBody.create(MEDIA_TYPE, postData.toString());
                    System.out.println("poststring:"+body);

                    final Request request = new Request.Builder()
                            .url("https://us-central1-pypay-cloudserver.cloudfunctions.net/payment/androidnew")
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
                                Toast.makeText(getActivity(), "Signup successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        controller.navigate(R.id.action_signupcon_to_connect);

                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Email or Username already exists!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        System.out.println(response.body().string());
                        controller.navigate(R.id.action_signupcon_to_signup);
                    }
                } catch (IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Connect error!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    e.printStackTrace();
                    controller.navigate(R.id.action_signupcon_to_signup);
                }
            }
        }).start();


    }


}



