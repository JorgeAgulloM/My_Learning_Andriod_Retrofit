package com.example.apitonterias2.tools.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.apitonterias2.R;
import com.example.apitonterias2.tools.ToolsActivity;

public class ToolsPhoto_Fragment extends Fragment {

    ImageView imageNewPhoto;
    ToolsActivity activity;
    Intent intent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_photo_fragment, container, false);

        imageNewPhoto = view.findViewById(R.id.imageNewPhoto);
        activity = new ToolsActivity();

        intent = activity.sendData();

        if (intent != null){
            insertPhoto();
        }

        return view;
    }

    public void receibeData(Intent data){
        intent = data;
    }

    public void insertPhoto(){
        Bundle bundle = intent.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        ImageView image = imageNewPhoto;
        image.setImageBitmap(bitmap);
    }


}
