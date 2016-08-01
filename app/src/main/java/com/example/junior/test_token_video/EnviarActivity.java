package com.example.junior.test_token_video;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;

public class EnviarActivity extends AppCompatActivity {

    private VideoView video;
    private Button btnGaleria;
    private Button btnTirar;
    private Button btnEnviar;
    Uri uri;
    Uri videoURI;
    byte[] videoByte;
    File videoFileStream;
    private final int MAX_CONTENT_LEN = 70;
    private final int SELECT_VIDEO = 1444;
    private final int REQUEST_VIDEO_CAPTURE = 1999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);
        uri = Uri.parse("vazio");
        video = (VideoView) findViewById(R.id.video);

        btnGaleria = (Button) findViewById(R.id.galeria);
        btnTirar = (Button) findViewById(R.id.tirar);
        btnEnviar = (Button) findViewById(R.id.enviar);


        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent.createChooser(intent,"select"),SELECT_VIDEO);


            }
        });

        btnTirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
               videoURI=getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,values);

               Intent takeVideoIntent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,videoURI);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);

                if (takeVideoIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
                }
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EnviarActivity.this, ""+videoFileStream.toString(), Toast.LENGTH_SHORT).show();
                /*try {
                    enviar(videoFileStream);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

                SendVideoAsyncTask sendVideoTask = new SendVideoAsyncTask(
                        EnviarActivity.this);
                sendVideoTask.execute();

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_CAPTURE) {
                videoURI=data.getData();
                loadVideoFromArchive(videoURI);
            } else if (requestCode == SELECT_VIDEO) {
                videoURI = data.getData();
                loadVideoFromArchive(videoURI);
            }
        }
    }

    private void loadVideoFromArchive(Uri videoUri){
        String[] filePathColumn = { MediaStore.Video.Media.DATA };

        Cursor cursor = getContentResolver().query(videoUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        String videoPath = cursor.getString(cursor
                .getColumnIndex(filePathColumn[0]));

        cursor.close();

        videoFileStream = new File(videoPath);

    }

    public void enviar(File file) throws JSONException, UnsupportedEncodingException {

        JSONObject video=new JSONObject();
        video.put("video",file);
        JSONObject model=new JSONObject();
        model.put("test",video);

        Log.e("<<<<<<<<<>>>>>>>>>",model.toString());

        StringEntity entity=new StringEntity(model.toString());
        VotaRestClient client=new VotaRestClient();
        client.post(EnviarActivity.this, "/tests.json", entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("><<<<<<<<<<<<<<<<<<<",new String(responseBody));
                Toast.makeText(EnviarActivity.this, "Deu certo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("<<<<<<<<<<<<<>>>>>>>>>>",error.toString());
                Toast.makeText(EnviarActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class SendVideoAsyncTask extends
            AsyncTask<Context, Integer, JSONObject> {
        private ProgressDialog progressDialog;

        public SendVideoAsyncTask(Context context) {
            progressDialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            progressDialog.setTitle("SDA");
            progressDialog.setMessage("sadsad");

            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Context... params) {
            String url = VotaRestClient.BASE_URL
                    + "/tests.json";

            JSONObject json = null;

            try {
                MultipartEntityBuilder meb = MultipartEntityBuilder
                        .create();

                meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                if (videoFileStream != null) {
                    meb.addBinaryBody(
                            "test[video]",
                            videoFileStream,
                            ContentType.DEFAULT_BINARY,
                            "video-"
                                    + SecondScreenHelper
                                    .getFormatedData(SecondScreenHelper
                                            .getTimeCurrent())
                                    + ".mp4");
                }

                String response = SecondScreenHelper.makePOSTRequest(url,
                        meb);

                if (response != null) {
                    json = new JSONObject(response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            try {
                if (json.getBoolean("success")
                        || json.getInt("code") == 504) {
                    Toast.makeText(EnviarActivity.this,
                            "Deu certo",
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(EnviarActivity.this, json.getString("info"),
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
