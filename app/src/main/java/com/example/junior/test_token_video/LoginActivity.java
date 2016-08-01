package com.example.junior.test_token_video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    EditText title,description;
    Button btn;
    AsyncHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        client=new AsyncHttpClient();

        title=(EditText) findViewById(R.id.title);

        description=(EditText) findViewById(R.id.description);

        btn=(Button) findViewById(R.id.enviar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
            }
        });
    }

    public void enviar(){
        JSONObject object=new JSONObject();
        JSONObject model=new JSONObject();
        HttpEntity entity=null;

        try {
            object.put("email",title.getText().toString());
            object.put("password",description.getText().toString());

            model.put("user",object);

            entity=new StringEntity(model.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(this, "http://192.168.1.2:3000/sessions",entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(LoginActivity.this, "Enviado", Toast.LENGTH_SHORT).show();
                Log.e("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>",new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                Log.e("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>",error.toString());

            }
        });
    }
}
