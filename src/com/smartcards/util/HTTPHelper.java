package com.smartcards.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.util.Log;

/**
 * Klasa HTTPHelper koja je zaužena za sve GET i POST akcije.
 */
public class HTTPHelper {

    private String hostURLPrefs;
    
    private String methodNamePrefs;
    
    private List<NameValuePair> nameValuePairs;
    
    private Activity activity;

    /**
     * Konstruktor koji prima podatke
     *
     * @param hostURLPrefs the host url prefs
     * @param methodNamePrefs the method name prefs
     * @param nameValuePairs the name value pairs
     * @param activity the activity
     */
    public HTTPHelper(String hostURLPrefs, String methodNamePrefs, List<NameValuePair> nameValuePairs, Activity activity) {
	this.setHostURLPrefs(hostURLPrefs);
	this.setMethodNamePrefs(methodNamePrefs);
	this.setNameValuePairs(nameValuePairs);
	this.setActivity(activity);
    }

    /**
     * Konstruktor koji prima podatke
     *
     * @param hostURLPrefs the host url prefs
     * @param methodNamePrefs the method name prefs
     * @param activity the activity
     */
    public HTTPHelper(String hostURLPrefs, String methodNamePrefs, Activity activity) {
	this.setHostURLPrefs(hostURLPrefs);
	this.setMethodNamePrefs(methodNamePrefs);
	this.setActivity(activity);
    }

    /**
     * Metoda koja pokreće GET request, a kao odgovor, vraća String reprezentaciju.
     *
     * @return the string
     */
    public String executeGETHelper() {

	try {

	    InputStream inputStream = getActivity().getResources().getAssets().open("config.properties");
	    Properties properties = PropertyReader.getInstance().readConfigProperties(inputStream);

	    String hostURL = properties.getProperty(getHostURLPrefs());
	    String methodName = properties.getProperty(getMethodNamePrefs());

	    URI uri = new URI(hostURL.concat(methodName));

	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(uri);
	    HttpResponse response = httpclient.execute(httpGet);

	    InputStream ips = response.getEntity().getContent();
	    BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

	    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
		throw new Exception("-1");
	    } else {
		StringBuilder sb = new StringBuilder();
		String s;
		while (true) {
		    s = buf.readLine();
		    if (s == null || s.length() == 0)
			break;
		    sb.append(s);
		    Log.d("HTTPGETHelper", sb.toString());
		}
		buf.close();
		ips.close();
		return sb.toString();
	    }

	} // TODO: register the new account here.
	catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	} catch (ClientProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (URISyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;

    }

    /**
     * Metoda koja pokreće POST request, a kao odgovor, vraća String reprezentaciju.
     *
     * @return the string
     */
    public String executePOSTHelper() {

	try {
	    InputStream inputStream = getActivity().getResources().getAssets().open("config.properties");
	    Properties properties = PropertyReader.getInstance().readConfigProperties(inputStream);

	    String hostURL = properties.getProperty(getHostURLPrefs());
	    String methodName = properties.getProperty(getMethodNamePrefs());

	    URI uri = new URI(hostURL.concat(methodName));

	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(uri);

	    httppost.setEntity(new UrlEncodedFormEntity(getNameValuePairs()));
	    HttpResponse response = httpclient.execute(httppost);

	    InputStream ips = response.getEntity().getContent();
	    BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

	    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
		throw new Exception("-1");
	    } else {
		StringBuilder sb = new StringBuilder();
		String s;
		while (true) {
		    s = buf.readLine();
		    if (s == null || s.length() == 0)
			break;
		    sb.append(s);
		    Log.d("HTTPPOSTHelper", sb.toString());
		}
		buf.close();
		ips.close();
		return sb.toString();
	    }
	} // TODO: register the new account here.
	catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return "-1";
	} catch (ClientProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return "-1";
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return "-1";
	} catch (URISyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return "-1";
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return "-1";
	}

    }

    /**
     * Gets the host url prefs.
     *
     * @return the host url prefs
     */
    public String getHostURLPrefs() {
	return hostURLPrefs;
    }

    /**
     * Sets the host url prefs.
     *
     * @param hostURLPrefs the new host url prefs
     */
    public void setHostURLPrefs(String hostURLPrefs) {
	this.hostURLPrefs = hostURLPrefs;
    }

    /**
     * Gets the method name prefs.
     *
     * @return the method name prefs
     */
    public String getMethodNamePrefs() {
	return methodNamePrefs;
    }

    /**
     * Sets the method name prefs.
     *
     * @param methodNamePrefs the new method name prefs
     */
    public void setMethodNamePrefs(String methodNamePrefs) {
	this.methodNamePrefs = methodNamePrefs;
    }

    /**
     * Gets the name value pairs.
     *
     * @return the name value pairs
     */
    public List<NameValuePair> getNameValuePairs() {
	return nameValuePairs;
    }

    /**
     * Sets the name value pairs.
     *
     * @param nameValuePairs the new name value pairs
     */
    public void setNameValuePairs(List<NameValuePair> nameValuePairs) {
	this.nameValuePairs = nameValuePairs;
    }

    /**
     * Gets the activity.
     *
     * @return the activity
     */
    public Activity getActivity() {
	return activity;
    }

    /**
     * Sets the activity.
     *
     * @param activity the new activity
     */
    public void setActivity(Activity activity) {
	this.activity = activity;
    }

}
