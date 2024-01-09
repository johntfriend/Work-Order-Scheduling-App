package sru.edu.Group1.WorkOrders;

import org.junit.jupiter.api.Test;

public class SepertaingEmailsTest {

	@Test
	public void serperateEmails()
	{
		String [] emails = {};
		
		String concurrentEmails = "tlb1024@sru.edu,"
				+ "fake@sru.edu,"
				+ "bmw105@sru.edu";
		
		emails = concurrentEmails.split(",");
		
		System.out.println(emails[1]);
	}
	
}
