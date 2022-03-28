package com.example.spm_basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    ImageView imageView;
    DrawerLayout drawerLayout;
    FloatingActionButton fab_dial, fab_whatsapp;
    ImageView img_SMH, img_BOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        imageView = findViewById(R.id.img_menu);
        navigationView = findViewById(R.id.navigation_View);
        fab_dial = findViewById(R.id.fab_dial);
        fab_whatsapp = findViewById(R.id.fab_wa);
        img_SMH = findViewById(R.id.grid_img_SMH);
        img_BOP = findViewById(R.id.grid_img_BOP);

        //onClick event for be our partner banner
        img_BOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BeOurPartner.class);
                startActivity(intent);
            }
        });

        //onClick event for shift my home grid banner
        img_SMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShiftMyHome.class);
                startActivity(intent);
            }
        });

        //to set colored icons in navigation drawer+
        navigationView.setItemIconTintList(null);


        //set onClick listener to menu icon to open drawer
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //dialer code
        fab_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "918291332142"));
                startActivity(intent);
            }
        });


        //whatsapp code
        fab_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = "918291332142";
                String message = "hi";
                boolean installed = isWhatsappInstalled("com.whatsapp");
                if (installed) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + smsNumber +
                            "&text" + message));
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Whatsapp not Installed", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);
                }
            }
        });


    }

    private boolean isWhatsappInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean whatsappInstalled = true;
        try {
            pm.getPackageInfo(uri, 0);
//            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        return whatsappInstalled;
    }

}




//}