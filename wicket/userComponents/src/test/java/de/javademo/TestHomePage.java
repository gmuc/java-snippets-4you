package de.javademo;

import de.javademo.tabwindow.StartTabList2Level;
import de.javademo.tabwindow.WicketApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester ttt eee
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(StartTabList2Level.class);

		//assert rendered page class
		tester.assertRenderedPage(StartTabList2Level.class);
	}
}
