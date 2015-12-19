package com.chrisdixon.mobilepatientrecord;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


// TODO: Auto-generated Javadoc
/**
 * The Class PostMedicalLogData.
 */
public class PostMedicalLogData {

	
	/**
	 * Instantiates a new post medical log data.
	 */
	public PostMedicalLogData(){}
	
	/**
	 * Excute post.
	 *
	 * @param username the username
	 * @param log the log
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static String excutePost(String username, String log) throws UnsupportedEncodingException
	  {
		String urlParameters = "username="+username+"&log="+log;
		String target = ("http://homepages.cs.ncl.ac.uk/c.dixon4/postMedicalProfessionalLog.php");
	    URL url;
	    HttpURLConnection con = null;  
	    try {
	      //Create connection
	      url = new URL(target);
	      con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("POST");
	      con.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      con.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      con.setRequestProperty("Content-Language", "en-US");  
				
	      con.setUseCaches (false);
	      con.setDoInput(true);
	      con.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  con.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = con.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(con!= null) {
	        con.disconnect(); 
	      }
	    }
	  }
	
}