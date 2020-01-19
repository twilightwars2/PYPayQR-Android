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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button log;
        log=getView().findViewById(R.id.loginbutton);
        final TextInputEditText uid;
        uid=getView().findViewById(R.id.logID);
        final EditText pass;
        pass=getView().findViewById(R.id.logpass);
        TextView remind;
        remind=getView().findViewById(R.id.remindsignbtn);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);

        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller=Navigation.findNavController(v);
                controller.navigate(R.id.action_login_to_signup);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String uid1 =uid.getText().toString();
                    String pass1=pass.getText().toString();
                if((!uid1.equals(""))&&(!pass1.equals(""))){
                    uid.setEnabled(false);
                    pass.setEnabled(false);
                    log.setEnabled(false);
                    pref.edit()
                            .putString("username", uid1)
                            .putString("password", pass1)
                            .apply();
                    NavController controller=Navigation.findNavController(v);
                    controller.navigate(R.id.action_login_to_logincon);
                }
                else
                    Toast.makeText(getActivity(), "Please fill in username and password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
