package com.example.xamo.qrcodeipo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();

        Bundle b = getIntent().getExtras();

        String url = b.getString("url", "");
        WebView webView = findViewById(R.id.webviewqrAct);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Product.this, InfoProducts.class));
    }
}
