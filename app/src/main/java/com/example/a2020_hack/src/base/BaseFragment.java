package com.example.a2020_hack.src.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public abstract class BaseFragment extends Fragment {

    public ProgressDialog loadingDialog;
    public AppCompatDialog progressDialog;

    public void showCustomToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
//            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            progressDialog.setContentView(R.layout.custom_dialog_loading);
            progressDialog.show();

        }


//        final ImageView img_loading_frame = progressDialog.findViewById(R.id.iv_frame_loading);
//        Glide.with(this).asGif().load(R.raw.here_there_loading).into(img_loading_frame);

    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    public abstract void setComponentView(View v);
}
