package com.example.pypayqr;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends Fragment {


    DatabaseReference databaseReference;




    public Signup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        final Button sign;
        sign=getView().findViewById(R.id.signupbutton);
        final TextInputEditText email,fname,lname,username;
        email=getView().findViewById(R.id.signemailv);
        fname=getView().findViewById(R.id.signfnamev);
        lname=getView().findViewById(R.id.signlnamev);
        username=getView().findViewById(R.id.signuserv);
        final EditText pass,conpass;
        pass=getView().findViewById(R.id.signpassv);
        conpass=getView().findViewById(R.id.signconv);
        TextView remind;
        remind=getView().findViewById(R.id.remindlogbtn);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("user", MODE_PRIVATE);

        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_signup_to_login);

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 =email.getText().toString();
                String fname1 =fname.getText().toString();
                String lname1 =lname.getText().toString();
                String username1 =username.getText().toString();
                String pass1 =pass.getText().toString();
                String conpass1 =conpass.getText().toString();

                if((!email1.equals(""))&&(!pass1.equals(""))&&(!fname1.equals(""))&&(!lname1.equals(""))&&(!username1.equals(""))&&(!conpass1.equals(""))){

                       if(Linkify.addLinks(email,Linkify.EMAIL_ADDRESSES)) {

                           if(pass1.equals(conpass1)) {

                               //http request

                               email.setEnabled(false);
                               pass.setEnabled(false);
                               sign.setEnabled(false);
                               fname.setEnabled(false);
                               lname.setEnabled(false);
                               conpass.setEnabled(false);
                               pref.edit()
                                       .putString("username", username1)
                                       .putString("lastname", lname1)
                                       .putString("firstname", fname1)
                                       .putString("email", email1)
                                       .putString("password", pass1)
                                       .apply();
                               NavController controller = Navigation.findNavController(v);
                               controller.navigate(R.id.action_signup_to_signupcon);



                           }else
                               Toast.makeText(getActivity(), "Password and Confirm Password must be same!", Toast.LENGTH_SHORT).show();
                       }else
                           Toast.makeText(getActivity(), "Please fill in the correct email!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Please fill in all blanks!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



