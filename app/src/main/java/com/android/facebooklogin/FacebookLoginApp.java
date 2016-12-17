package com.android.facebooklogin;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;


/**
 Nuestro proyecto tiene una clase Application que es una buena practica cuando queremos que ciertas
 cosas esten accesibles en toda nuesta aplicación

 Esta se ejecuta al momento que compilamos (Ejecutamos nusetra aplicación).
 */
public class FacebookLoginApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("HolaMundo","Entramos en onCreate");

        /*Agregamos como parametro el nombre del paquete*/
        mostrarHashKey_Facebook("com.android.facebooklogin");

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    /*Este metodo mostrarHashKey solo se utiliza para generar el clave Key Hash no tiene
    * otr afinalidad y es independiente de todo lo demas
    * */
    public void mostrarHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.android.facebooklogin",     /*8*/
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
    /*9*/       Log.d("MiHashKey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    public void mostrarHashKey_Facebook(String packageName){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("MiHashKey :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }

    }

}

