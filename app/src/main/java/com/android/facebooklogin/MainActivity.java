package com.android.facebooklogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import android.view.View;
import android.content.Intent;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/*IMPRORTANTE: La Actividad por Default es MainActivity misma que así debe de estar configurado el AndoridManifest.xml
* y mims que dede de estar registrada en la aplicacion dada de alta en la pagina de Facebook*/

public class MainActivity extends AppCompatActivity {

    //Hacemos una vista de Publicidad-Banner
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*
        IMPORTANTE : Como va a entrar nuestra aplicación por default a la actividad de MainActivity debemos
         realizar la validacion si tiene session abierta o no por medio del AccessToken.getCurrentAccessToken = null ,
         si no tiene una sessio Abierta vamos a querer que vala a la actividad LoginActivity para que se loge

         */

        if(AccessToken.getCurrentAccessToken() == null){
            //Cuando es null entonces indica que no tenemos la sessión iniciada
            //Cuando pase esto vamos a querer que vaya a la pantalla de login para que inicie el proceso

                   /*
        Cuando el usuario esta en la pantalla principal significa que tiene una sessión por lo tanto
        tiene que existir una opción de salir de sessión en algun momneto. esta opcion puede estar
        en un menu o un botón dentro de la aplicación
        */


            goLoginScreen();
        }



        //Codigo para Publicidad-Banner
        adView = (AdView)findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    private void goLoginScreen() {

        Intent intent  = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*
    Entonces cuando nuestro usuario este en la pantalla principal (MainActivity significa que tiene una sessión
    por lo tanto tiene que existir la opcion de cerrar sessión en algun momento, esta acción puede estar en un botón
    o en un menu.

     Aqui ponemos la linea para cerrar sessión que podemos usar en cualquier parte de nuestra aplicación
         * y cuando cerramos sessión lo que generalmente queremos es que salga de la aplicación y que se
         * vaya a la pantalla de login nuevamente
            */
    public void logout(View view){
        /*
          * Primera Observación: Esta linesa no significa que cierre sessión en FACEBOOK la sessión se mantiene abierta.
          * la manera de saberlo es ingresando a Facebook (Ya no nos pedira logeo)
          *
          * Segunda observación: Esta sessión va a permanecer hastas que cerremos sessión e incluso podemos reiniciar
          * el dispositivo y cerrar completamente la aplicación y cuando volvamos a ingresar la sessión va a continuar
           *
           * */
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }


    /*===================== METODOS PARA Publicidad-Banner ==============*/
    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

}
