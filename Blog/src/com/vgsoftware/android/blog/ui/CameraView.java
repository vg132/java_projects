package com.vgsoftware.android.blog.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback
{
	private boolean _preview = true;
	private Camera _camera;
	private ArrayList<OnPictureTakenListener> _pictureTakenListeners;

	public CameraView(Context context)
	{
		super(context);
		initCameraView();
	}

	public CameraView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initCameraView();
	}

	public CameraView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initCameraView();
	}

	private void initCameraView()
	{
		SurfaceHolder mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if (_preview)
		{
			_camera.stopPreview();
		}
		Camera.Parameters p = _camera.getParameters();
		p.setPreviewSize(width, height);
		_camera.setParameters(p);
		try
		{
			_camera.setPreviewDisplay(holder);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		_camera.startPreview();
		_preview = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		_camera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		_camera.stopPreview();
		_preview = false;
		_camera.release();
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback()
	{
		@Override
		public void onPictureTaken(byte[] imageData, Camera c)
		{
			Picture pic=new Picture();
			pic.setPictureData(imageData);
			OnPictureTaken(pic);
		}
	};
  
  public void TakePicture()
  {
  	_camera.takePicture(null,null,mPictureCallback);
  }
  
	public synchronized void setOnPictureTakenListener(OnPictureTakenListener listener)
	{
		if(_pictureTakenListeners==null)
		{
			_pictureTakenListeners=new ArrayList<OnPictureTakenListener>();
		}
		_pictureTakenListeners.add(listener);
	}
	
	private void OnPictureTaken(Picture picture)
	{
		if(_pictureTakenListeners!=null)
		{
			for(OnPictureTakenListener listener : _pictureTakenListeners)
			{
				listener.OnPictureTaken(picture);
			}
		}
	}
}
