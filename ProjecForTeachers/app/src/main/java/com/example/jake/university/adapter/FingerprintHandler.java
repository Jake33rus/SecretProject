package com.example.jake.university.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import com.example.jake.university.LockScreenActivity;
import com.example.jake.university.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static androidx.core.content.ContextCompat.startActivity;


@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends  FingerprintManager.AuthenticationCallback
{
    // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
    // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!//

    private CancellationSignal cancellationSignal;
    private Context context;
    private String login;
    private SharedPreferences checkingLogin;
    public static final String APP_PREFERENCES = "login_vault";
    public static final String APP_SAVED_LOGIN = "saved_login";
    public static final String APP_SAVED_NREC = "saved_nrec";


    public FingerprintHandler(Context mContext, String login, SharedPreferences checkingLogin)
    {
        context = mContext;
        this.login=login;
        this.checkingLogin=checkingLogin;
    }

    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    //onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//

    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs//

        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }
    @Override

    //onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device//

    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    @Override

    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override

//onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result)
    {
        if (checkingLogin.contains(APP_SAVED_LOGIN))
        {
            String buf = checkingLogin.getString(APP_SAVED_LOGIN, "DEFAULT");
            if(buf.equals(login))
            {starterActivity(context, checkingLogin);}
        }
    }

    public static void starterActivity(Context context, SharedPreferences sp)
    {
        //context.startActivity(new Intent(context, MainActivity.class));

        String buf = sp.getString(APP_SAVED_NREC, "DEFAULT");
        Intent toLock = new Intent(context, LockScreenActivity.class);
        toLock.putExtra("nrec", buf);
        context.startActivity(toLock);

    }

}

