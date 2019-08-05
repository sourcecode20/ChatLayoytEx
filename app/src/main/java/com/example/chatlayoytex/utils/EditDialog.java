package com.example.chatlayoytex.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.callback.DialogResponceCallback;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class EditDialog {

    private DialogPlus dialogPlus;
    private EditText edit;
    Button cancle,save;



    public EditDialog(Context context, DialogResponceCallback<String> dialogResponceCallback) {

        dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_name))
                .setGravity(Gravity.CENTER)
                .setExpanded(true)
                .create();

        LinearLayout layout = (LinearLayout) dialogPlus.getHolderView();

        cancle = layout.findViewById(R.id.cancel);
        save = layout.findViewById(R.id.save);
        edit = layout.findViewById(R.id.edit);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
                dialogResponceCallback.callback(edit.getText().toString());
            }
        });

    }


    public void show() {
        dialogPlus.show();
    }

    public void dismiss() {
        dialogPlus.dismiss();
    }
}
