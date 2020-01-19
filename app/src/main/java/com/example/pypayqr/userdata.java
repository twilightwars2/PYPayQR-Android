package com.example.pypayqr;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class userdata extends Fragment {


    public userdata() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userdata, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);


        final SharedPreferences.Editor editor = pref.edit();


        final String username1 =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");
        final String email1 =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("email", "");
        final String firstname1 =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("firstname", "");
        final String lastname1 =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("lastname", "");
        final String balance1 =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("balance", "");

        TextView username,email,firstname,lastname,balance,title;
        username=getView().findViewById(R.id.value_user);
        username.setText(username1);
        email=getView().findViewById(R.id.value_email);
        email.setText(email1);
        firstname=getView().findViewById(R.id.value_first);
        firstname.setText(firstname1);
        lastname=getView().findViewById(R.id.value_last);
        lastname.setText(lastname1);
        balance=getView().findViewById(R.id.value_bal);
        balance.setText(balance1);
        title=getView().findViewById(R.id.who);
        title.setText(firstname1+"'s personal data");

        Button payment,logout;
        payment=getView().findViewById(R.id.paymentbtn);
        logout=getView().findViewById(R.id.logoutbtn);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_userdata_to_QR);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.commit();
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_userdata_to_connect);

            }
        });


    }
}
