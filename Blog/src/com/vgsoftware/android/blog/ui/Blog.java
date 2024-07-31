package com.vgsoftware.android.blog.ui;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.vgsoftware.android.blog.R;
import com.vgsoftware.android.blog.Utility;
import com.vgsoftware.android.location.LocationService;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

public class Blog extends Activity
{	
	private Button _cameraButton;
	private CameraView _cameraView;
	private EditText _headingEditText;
	private Button _sendButton;
	private EditText _mainBodyEditText;
	private byte[] _picture;

	private void setupLocalVariables()
	{
		_cameraView=(CameraView)findViewById(R.id.CameraControl2);
		_cameraButton=(Button)findViewById(R.id.CameraButton);
		_headingEditText=(EditText)findViewById(R.id.HeadingEditText);
		_mainBodyEditText=(EditText)findViewById(R.id.MainBodyEditText);
		_sendButton=(Button)findViewById(R.id.SendButton);
	}

	private void setupListeners()
	{
		_cameraView.setOnPictureTakenListener
		(
			new OnPictureTakenListener()
			{
				@Override
				public void OnPictureTaken(Picture picture)
				{
					setPicture(picture.getPictureData());
				}
			}
		);
		
		_cameraButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				_cameraView.TakePicture();
			}
		});
		
		_sendButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PostEntry();
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Utility.setContext(this);
		LocationService.getInstance().Start();
		setContentView(R.layout.main);
		setupLocalVariables();
		setupListeners();
	}
	
	private void PostEntry()
	{
    try
		{
      int TIMEOUT_MILLISEC = 90000;
      HttpParams httpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
      HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
      HttpClient client = new DefaultHttpClient(httpParams);

      HttpPost request = new HttpPost("http://XXX/Upload.aspx");
 
      MultipartEntity  entity = new MultipartEntity();

      entity.addPart("Heading",new StringBody(_headingEditText.getText().toString(),Charset.forName("UTF-8")));
      entity.addPart("MainBody",new StringBody(_mainBodyEditText.getText().toString(),Charset.forName("UTF-8")));
      entity.addPart("Long",new StringBody(Double.toString(LocationService.getInstance().getCurrentLocation().getLongitude()),Charset.forName("UTF-8")));
      entity.addPart("Lat",new StringBody(Double.toString(LocationService.getInstance().getCurrentLocation().getLatitude()),Charset.forName("UTF-8")));
      entity.addPart("userfile[]", new InputStreamBody(new ByteArrayInputStream(getPicture()), "Image.jpg"));
      request.setEntity(entity);
			HttpResponse response = client.execute(request);
		}
    catch(Exception ex)
    {
    }
	}

	private void setPicture(byte[] picture)
	{
		_picture=picture;
	}
	
	private byte[] getPicture()
	{
		return _picture;
	}
}