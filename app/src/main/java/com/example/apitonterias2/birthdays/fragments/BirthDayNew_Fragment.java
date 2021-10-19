package com.example.apitonterias2.birthdays.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebIconDatabase;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.apitonterias2.R;
import com.example.apitonterias2.birthdays.BirtDayActivityMain;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class BirthDayNew_Fragment extends Fragment {

    ImageView imageBirthDay;
    MaterialAutoCompleteTextView txtNameBirthDay;
    MaterialButton btnAddImage, btnNewBirthDay, btnAddDate, btnCloseDP;
    WebView webView;
    DatePicker datePicker;
    LinearLayout layoutSearImage;

    String dateBirth, urlDir;
    int stateFragment;


    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.birthdays_new_fragment, container, false);

        imageBirthDay = view.findViewById(R.id.imageBirthDay);
        txtNameBirthDay = view.findViewById(R.id.txtNameBirthDay);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        btnNewBirthDay = view.findViewById(R.id.btnNewBirthDay);
        btnAddDate = view.findViewById(R.id.btnAddDate);
        btnCloseDP = view.findViewById(R.id.btnCloseDP);
        webView = (WebView) view.findViewById(R.id.webView);
        layoutSearImage = view.findViewById(R.id.layoutSearImage);
        datePicker = view.findViewById(R.id.datePicker);
        long toDay = Calendar.DAY_OF_MONTH;
        datePicker.setMaxDate(0);

        stateFragment = 0;

        urlDir = "https://www.google.com/search?tbm=isch&source=hp&biw=1920&bih=937&ei=TbISYIHyD8OdlwTy9pJg&q=fiesta+de+cumplea%C3%B1os&oq=fiesta+de+cumplea%C3%B1os&gs_lcp=CgNpbWcQAzIFCAAQsQMyAggAMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIADICCABQoyBYmj9gyEBoAXAAeAGAAY4CiAG3D5IBBjE2LjMuMZgBAKABAaoBC2d3cy13aXotaW1nsAEA&sclient=img&ved=0ahUKEwjBzK3J1L7uAhXDzoUKHXK7BAwQ4dUDCAc&uact=5";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(urlDir);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                switch (stateFragment) {
                    case 0:
                        getActivity().finish();
                        break;
                    case 1:
                        datePicker.setVisibility(View.GONE);
                        break;
                    case 2:
                        if (webView.canGoBack()) {
                            webView.goBack();
                        } else {
                            layoutSearImage.setVisibility(View.GONE);
                            Picasso.get().load(((BirtDayActivityMain) Objects.requireNonNull(getActivity())).clipBoard()).into(imageBirthDay);
                            stateFragment = 0;
                        }
                        break;
                    case 3:

                        break;
                    default:
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        btnAddDate.setOnClickListener(v -> {
            datePicker.setVisibility(View.VISIBLE);
            stateFragment = 1;
        });

        btnCloseDP.setOnClickListener(v -> {
            datePicker.setVisibility(View.GONE);
            dateBirth = datePicker.getDayOfMonth() + "/" +
                    datePicker.getMonth() + "/" +
                    datePicker.getYear();
            btnAddDate.setText("Fecha " + dateBirth);
            stateFragment = 0;
        });


        btnAddImage.setOnClickListener(v -> {

            layoutSearImage.setVisibility(View.VISIBLE);
            stateFragment = 2;


            webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {

                final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();

                request.setMimeType(mimetype);

                String coockies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("coockie", coockies);

                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Descargando archivo...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));


            });


        });

        return view;

    }

}

