package se.cth.hedgehogphoto.map.geocoding.model;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.Before;
import org.junit.Test;

public class URLCreatorTest {
	public URLCreator instance;

	@Before
	public void setUp() throws Exception {
		instance = URLCreator.getInstance();
	}

	@Test
	public void testGetInstance() {
		instance = URLCreator.getInstance();
		assertTrue(instance != null);
		instance = null;
		instance = URLCreator.getInstance();
		assertTrue(instance != null);
	}

	@Test
	public void testInvokeLater() {
		//do i need to test this?
	}

	@Test
	public void testQueryGeocodingURL() {
		String path = "http://nominatim.openstreetmap.org/search?format=xml&addressdetails=0&q=";
		
		String query = null; //handle null-query
		assertTrue(path.equals(instance.queryGeocodingURL(query).toString()));
		
		query = ""; //handle empty query
		assertTrue(path.equals(instance.queryGeocodingURL(query).toString()));
		
		query = "london"; //handle query
		String queryPath = path + query;
		assertTrue(queryPath.equals(instance.queryGeocodingURL(query).toString()));
		
		query = "new york"; //handle space in search
		queryPath = path + query;
		assertTrue(!queryPath.equals(instance.queryGeocodingURL(query).toString()));
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be 'new+york' now, ie URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryGeocodingURL(query).toString());
			assertTrue(queryPath.equals(instance.queryGeocodingURL("new york").toString())); //has to use old query-string
		}
		
		
		query = "g�teborg"; //handle scandinavian letter �
		
		try {
			query = URLEncoder.encode(query, "UTF-8"); //should be URLcompatible
		} catch (UnsupportedEncodingException e) {
			//fail?
		} finally {
			queryPath = path + query;
			System.out.println(queryPath + "\nvs\n" + instance.queryGeocodingURL("g�teborg").toString());
			assertTrue(queryPath.equals(instance.queryGeocodingURL("g�teborg").toString())); //has to use old query-string
		}
	}
	
	private URL createURL(String path) { //TODO: Remove method?
		URL url = null;
		try {
			return new URL(path);
		} catch (MalformedURLException e) {
			return null;
//			fail("Could not create URL");
		} 
	}

}
