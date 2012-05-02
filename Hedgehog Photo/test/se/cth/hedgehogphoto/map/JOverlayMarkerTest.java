package se.cth.hedgehogphoto.map;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;

public class JOverlayMarkerTest {
	private AbstractJOverlayMarker label;
	private int xPos = 50;
	private int yPos = 70;

	@Before
	public void setUp() throws Exception {
		Picture pic = new Picture();
		Location location = new Location();
		location.setLongitude(xPos);
		location.setLatitude(yPos);
		pic.setLocation(location);
		label = new JMarker(pic);
	}

	@Test
	public void testGetXOffset() {
		int x = label.getXOffset();
		assertTrue(x == 13); //19x19-icon
		
		label.setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //change icon to one sized 19x19
		x = label.getXOffset();
		assertTrue(x == 9);
	}

	@Test
	public void testGetYOffset() {
		int y = label.getYOffset();
		assertTrue(y == 26); //26x26-icon
		
		label.setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //change icon to one sized 19x19
		y = label.getYOffset();
		assertTrue(y == 19);
	}

	@Test
	public void testGetMouseListener() {
		/* Not testable? */
	}

	@Test
	public void testJMarker() {
		assertTrue(label.getImageIcon() != null);
		assertTrue(label.getImageIcon() != null);
		assertTrue(label.getComponentWidth() == label.getImageIcon().getIconWidth());
	}

	@Test
	public void testMove() {
		Point p = label.getLocation();
		int x = 3;
		int y = 2;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
		
		x = -1;
		y = 5;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
		
		x = -4;
		y = -5;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
		
		x = 0;
		y = 0;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
	}

	@Test
	public void testInit() {
		Point p = new Point(xPos, yPos);
		assertTrue(!label.getLocation().equals(p));
//		p.x -= label.getXOffset();
//		p.y -= label.getYOffset();
//		assertTrue(label.getLocation().equals(p)); //doesn't work since label gets a Locationobject with long/lat xPos and yPos, not bounds.
	}

	@Test
	public void testGetPoint() {
		Point p = label.getLocation();
		p.x += label.getXOffset();
		p.y += label.getYOffset();
		assertTrue(p.equals(label.getPoint()));
		label.setBounds(55, 100);
		p = new Point(55,100);
		p.x += label.getXOffset();
		p.y += label.getYOffset();
		assertTrue(p.equals(label.getPoint()));
	}

	@Test
	public void testGetXPosition() {
		int x = label.getX();
		assertTrue(x + label.getXOffset() == label.getXPosition());
	}

	@Test
	public void testGetYPosition() {
		int y = label.getY();
		assertTrue(y + label.getYOffset() == label.getYPosition());
	}

	@Test
	public void testSetPixelPosition() {
		Point p = label.getLocation();
		label.setPixelPosition(xPos, yPos);
		assertTrue(!p.equals(label.getLocation()));
		assertTrue(!label.getLocation().equals(new Point(xPos, yPos)));
	}

	@Test
	public void testPropertyChange() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetImageIcon() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetImageIcon() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetIconHeight() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetIconHeight() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetIconWidth() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetIconWidth() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetProperIconSize() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIntersects() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetBoundsIntInt() {
		//fail("Not yet implemented");
	}

}