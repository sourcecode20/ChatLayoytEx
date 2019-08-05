package com.example.chatlayoytex.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import com.example.chatlayoytex.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class Loader {

    public Loader(DialogPlus dialogPlus) {
        this.dialogPlus = dialogPlus;
    }

    private DialogPlus dialogPlus;


    public Loader(Context context) {
        dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.activity_loader))
                .setContentBackgroundResource(Color.TRANSPARENT)
                .setExpanded(true)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public void show() {
        dialogPlus.show();

    }

    public void dismiss() {
        dialogPlus.dismiss();
    }
}
