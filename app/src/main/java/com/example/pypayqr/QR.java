package com.example.pypayqr;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class QR extends Fragment {


    public QR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String userid =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("ID", "");
        final String firstname =this.getActivity().getSharedPreferences("user", MODE_PRIVATE)
                .getString("firstname", "");

        Button showqr;
        showqr=getView().findViewById(R.id.showqrbtn);
        TextView name;
        name=getView().findViewById(R.id.welcomeback);
        name.setText("Welcome back,"+firstname+"!");
        Shader shader = new LinearGradient(0, 0, 0, name.getTextSize(), Color.RED, Color.BLUE,
                Shader.TileMode.CLAMP);
        name.getPaint().setShader(shader);
        showqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View imageEnterView = layoutInflater.inflate(R.layout.qrlayout, null);
                ImageView ivCode = (ImageView) imageEnterView.findViewById(R.id.qrdia);
                BarcodeEncoder encoder = new BarcodeEncoder();
                try{
                    Bitmap bit = encoder.encodeBitmap(userid, BarcodeFormat.QR_CODE,250,250);
                    ivCode.setImageBitmap(bit);
                }catch (WriterException e){
                    e.printStackTrace();
                }

                new AlertDialog.Builder(getActivity())
                        .setView(imageEnterView)
                        .setCancelable(false)
                        .setPositiveButton("Cancel", null).show();
            }
        });

        Button gouser;
        gouser=getView().findViewById(R.id.gouser);
        gouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller=Navigation.findNavController(v);
                controller.navigate(R.id.action_QR_to_load);
            }
        });

    }


}
