package com.example.orderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class VDialog extends AppCompatDialogFragment {
    EditText vcode;
    VDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle("Verification")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String vercode=vcode.getText().toString();
                        listener.checkvcode(vercode);
                    }
                });
        vcode=(EditText)view.findViewById(R.id.vcode);
        return builder.create();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener=(VDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
            "must implement VDialogListener");
        }

    }

    public interface VDialogListener{
        void checkvcode(String vcode);
    }
}
