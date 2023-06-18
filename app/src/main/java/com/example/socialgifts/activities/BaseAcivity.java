package com.example.socialgifts.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class BaseAcivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Salir de la aplicacion")
                .setMessage("Estas seguro que quieres salir de la aplicacion?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .create().show();
    }
}
