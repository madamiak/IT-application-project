package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResponseTest
{
	private Response testObject;
	
	@Test
	public void whenConvertingToJson_ShouldReturnObjectInJsonFormat()
	{
		testObject = new Response();
		assertTrue(testObject.toJson().toString().contains("code"));
		assertTrue(testObject.toJson().toString().contains("message"));
		assertTrue(testObject.toJson().toString().contains("data"));
	}

}
