package com.example.androiddeveloper.notificationdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    Button bt_login;
    EditText et_username,et_password;
    public static final String TAG = "Login";
    ProgressDialog pDialog;
    String GCMId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_password= (EditText)findViewById(R.id.input_email);
        et_username = (EditText)findViewById(R.id.input_password);
        bt_login = (Button) findViewById(R.id.btn_enter);

        //progress dialog programatically
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FetchGCMid().execute();
            }
        });

    }

    public class CheckLogin extends AsyncTask<Void,Void,Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                    JSONParser jsonParser = new JSONParser();
                    jsonParser.makeServiceCall("http://openspace.tranetech.com/mis/gcm_chat/insert_gcm_id.php?gcm_registration_id="+GCMId.toString());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.i(TAG, "onPostExecute: Pass");
            }
        }


    public class FetchGCMid extends AsyncTask<Void,Void,Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
//                    showpDialog();
                }

                @Override
                protected Void doInBackground(Void... params) {

                    try {
                        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                        GCMId = instanceID.getToken("3608079128",
                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "GCM_ID : "+ GCMId);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    new CheckLogin().execute();
                }
            }

    //show dialog
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //hide dialog
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
