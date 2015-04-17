package com.example.sudukousingcamapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.googlecode.tesseract.android.TessBaseAPI;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "";
	public static String recognizedText;
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/SudokuUsingCamApp/";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

		for (String path : paths) 
		{
			File dir = new File(path);
		}
		if (!(new File(DATA_PATH + "tessdata/eng.traineddata")).exists()) 
		{
			try 
			{
				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/eng.traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/eng.traineddata");

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) 
				{
					out.write(buf, 0, len);
				}
				in.close();
				//gin.close();
				out.close();
				
			} catch (IOException e) {}
		}

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public void openCamera(View view)
	{
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);

	}
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0)
		{
			Bitmap sudImage = (Bitmap)data.getExtras().get("data");
			sudImage = sudImage.copy(Bitmap.Config.ARGB_8888, true);
			
			Intent intent2 = new Intent(this, PrintActivity.class);
			
			TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(DATA_PATH, "eng");
			baseApi.setImage(sudImage);
			String recognizedText = baseApi.getUTF8Text();
			intent2.putExtra(EXTRA_MESSAGE, recognizedText);
			baseApi.end();
			startActivity(intent2);
		}
	}

}
