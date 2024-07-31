package com.vgsoftware.android.hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class Hello extends TabActivity
{
	private LinearLayout _realTimeLinearLayout=null;
	
	private Spinner _transportTypeSpinner=null;
	private AutoCompleteTextView _stationTextView=null;
	private Button _showTimesButton=null;
	private TextView _debugTextView=null;

	private void setupControls()
	{
		//Layouts
		_realTimeLinearLayout=(LinearLayout)findViewById(R.id.RealTimeLinearLayout);

		//Controls
		_transportTypeSpinner=(Spinner)findViewById(R.id.TypeSpinner);
		_stationTextView=(AutoCompleteTextView)findViewById(R.id.StationTextView);
		_showTimesButton=(Button)findViewById(R.id.ShowTimesButton);
		_debugTextView=(TextView)findViewById(R.id.DebugTextView);
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupControls();
		TabHost mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("realTimeTab").setIndicator(getString(R.string.RealTimeTabName)).setContent(R.id.RealTimeLinearLayout));
		mTabHost.addTab(mTabHost.newTabSpec("favoriteTab").setIndicator(getString(R.string.FavoriteTabName)).setContent(R.id.textview1));

		_transportTypeSpinner.setOnItemSelectedListener(
			new AdapterView.OnItemSelectedListener()
			{
				@Override
				public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id)
				{
					switch(position)
					{
						case 1:
							setupStationTextView(R.array.CommuterTrainName);
							break;
						case 2:
							setupStationTextView(R.array.RoslagsbanaName);
							break;
						case 3:
							setupStationTextView(R.array.GreenLineName);
							break;
						case 4:
							setupStationTextView(R.array.RedLineName);
							break;
						case 5:
							setupStationTextView(R.array.BlueLineName);
							break;
						default:
							_stationTextView.setEnabled(false);
							_showTimesButton.setEnabled(false);
							break;
					}
				}

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView)
		    {
		    }
			});
		
		_showTimesButton.setOnClickListener(
			new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					String str=getRealTimeRawData("");
					if(str.length()>100)
					{
						_debugTextView.setText(str.substring(0,100));
					}
					else
					{
						_debugTextView.setText(str);
					}
				}
			});
		Log.d("test","test");
		mTabHost.setCurrentTab(0);
	}

	private String getRealTimeRawData(String url)
	{
		try
		{
			HttpClient hc = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://www.yahoo.com");
			HttpResponse rp = hc.execute(get);

			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				String content= generateString(rp.getEntity().getContent());
				Log.d("[RealTimeData]",content);
				return content;
			}
			else
			{
				Log.d("[RealTimeData]","No Data found");
			}
		}
		catch (IOException e)
		{
			Log.d("[RealTimeData]","IO Exception",e);
			return "";
		}
		return "";
	}
	
	public String generateString(InputStream stream)
	{
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffer = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();

		try
		{
			String cur;
			while ((cur = buffer.readLine()) != null)
			{
				sb.append(cur + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void setupStationTextView(int resourceId)
	{
  	ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,resourceId,android.R.layout.simple_dropdown_item_1line);
  	_stationTextView.setAdapter(adapter);
  	_stationTextView.setText("");
  	_stationTextView.setEnabled(true);
  	_showTimesButton.setEnabled(true);
	}
}