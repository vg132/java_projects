package com.vgsoftware.android.location;

import java.util.ArrayList;

import com.vgsoftware.android.blog.Utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService
{
	private static final int UpdateIntervalTime=300000;
	private static final int UpdateIntervalDistance=100;

	private static LocationService _instance;
	private LocationManager _locationManager;
	private Location _currentGPSLocation;
	private Location _currentNetworkLocation;
	private ArrayList<OnLocationChangeListener> _locationChangedListeners;
	
	private LocationService()
	{
		_locationManager=(LocationManager)Utility.getContext().getSystemService(Context.LOCATION_SERVICE);
	}

	public synchronized static LocationService getInstance()
	{
		if(_instance==null)
		{
			_instance=new LocationService();
		}
		return _instance;
	}
	
	public Location getCurrentLocation()
	{
		if(_currentGPSLocation!=null)
		{
			return _currentGPSLocation;
		}
		return _currentNetworkLocation;
	}
	
	public synchronized void setOnLocationChangeListener(OnLocationChangeListener listener)
	{
		if(_locationChangedListeners==null)
		{
			_locationChangedListeners=new ArrayList<OnLocationChangeListener>();
		}
		_locationChangedListeners.add(listener);
	}
	
	private void OnLocationChanged()
	{
		if(_locationChangedListeners!=null)
		{
			for(OnLocationChangeListener listener : _locationChangedListeners)
			{
				listener.OnLocationChanged(getCurrentLocation());
			}
		}
	}

	public void Start()
	{
		if(_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LocationService.UpdateIntervalTime, LocationService.UpdateIntervalDistance, _locationListener);
		}
		if(_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LocationService.UpdateIntervalTime, LocationService.UpdateIntervalDistance, _locationListener);
		}
	}

	public void Stop()
	{
		_locationManager.removeUpdates(_locationListener);
	}	

	private LocationListener _locationListener = new LocationListener()
	{

		public void onLocationChanged(Location location)
		{
			if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
			{
				_currentNetworkLocation=location;
				OnLocationChanged();
			}
			else if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
			{
				_currentGPSLocation=location;
				OnLocationChanged();
			}
		}

		public void onProviderDisabled(String provider)
		{
			if(LocationManager.NETWORK_PROVIDER.equals(provider))
			{
				_currentNetworkLocation=null;
			}
			else if(LocationManager.GPS_PROVIDER.equals(provider))
			{
				_currentGPSLocation=null;
			}
		}

		public void onProviderEnabled(String provider)
		{
		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	};
}
