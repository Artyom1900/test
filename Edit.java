package com.example.alexandr.login_server;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 04.08.14.
 */
public class Edit extends ActionBarActivity implements View.OnClickListener
{
    Button btnCamera, btnGalery,btnServer;

    EditText FName, LName, Email, Nik;
    ImageView ivCamera, image;
//    Socket socket = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    File directory;
    final int TYPE_PHOTO = 1;
    final int REQUEST_CODE_PHOTO = 1;


    final String TAG = "myLogs";
    ImageView ivPhoto;
//
    HttpClient httpClient = null;
    HttpPost httpPost = null;
//    private static final int REQUEST = 1;
//    public final int CAMERA_RESULT = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        try {
//            socket = new Socket("127.0.0.1", 6000);
//            dis = new DataInputStream(socket.getInputStream());
//            dos = new DataOutputStream(socket.getOutputStream());
//        }
//        catch (Exception e)
//        {
//
//        }

        createDirectory();
        Nik = (EditText) findViewById(R.id.Nik);
        Email = (EditText) findViewById(R.id.Email);
        FName = (EditText) findViewById(R.id.FName);
        LName = (EditText) findViewById(R.id.LName);
//        image = (ImageView) findViewById(R.id.imageView);
//        btnCamera = (Button) findViewById(R.id.btnGal);

//        btnGalery = (Button) findViewById(R.id.btnGal);
        ivPhoto = (ImageView) findViewById(R.id.imageView);


        String nikPer = getIntent().getStringExtra("NIK");
        String emailPer = getIntent().getStringExtra("EMAIL");

        Nik.setText(nikPer);
        Email.setText(emailPer);

        btnServer =  (Button) findViewById(R.id.btnServer);
        btnServer.setOnClickListener(this);


//        btnGalery =  (Button) findViewById(R.id.btnGal);
//        btnGalery.setOnClickListener(this);
//        btnCamera =  (Button) findViewById(R.id.btnCam);
//        btnCamera.setOnClickListener(this);

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://localhost:8080");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnServer:
            {
                Person p=new Person(FName.toString(), LName.toString(), Nik.toString(),Email.toString());
                Gson g= new Gson();
                String str=g.toJson(p);
                //Connect con=new Connect(str);

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("", str));


//                try {
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//                } catch (UnsupportedEncodingException e) {
//                    // writing error to Log
//                    e.printStackTrace();
//                }
//                try {
//                    HttpResponse response = httpClient.execute(httpPost);
//
//                    // writing response to log
//                    Log.d("Http Response:", response.toString());
//                } catch (ClientProtocolException e) {
//                    // writing exception to log
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // writing exception to log
//                    e.printStackTrace();
//
//                }
//                con.start();
//                try {
//

//                    Socket socket = new Socket("127.0.0.1",6002);
//                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//                    out.print(str);
//                    out.flush();
////                    socket = new Socket("127.0.0.1", 6000);
////                    dis = new DataInputStream(socket.getInputStream());
////                    dos = new DataOutputStream(socket.getOutputStream());
////                    dos.writeUTF(str);
////                    dos.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }break;
            case R.id.btnGal:
            {

            }break;
            case R.id.btnCam:
            {

            }break;


        }
    }

    public void onClickPhoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }

    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }
}

class Connect extends AsyncTask<Void, Void, Void> {

    PrintWriter out;
    String str;

    Connect(String str){
        this.str=str;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        try {
            socket = new Socket("192.168.0.103",6002);
            out=new PrintWriter(socket.getOutputStream(),true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        out.print(str);
        out.flush();
    }

}



//class Connect extends Thread implements Runnable
//{
//    private String str;
//    public Connect(String strum)
//    {
//        this.str=strum;
//    }
//    @Override
//    public void run()
//    {
//        Socket socket = null;
//        try {
//            socket = new Socket("127.0.0.1",6002);
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//            out.print(str);
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}