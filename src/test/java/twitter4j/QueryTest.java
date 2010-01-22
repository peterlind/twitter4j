package twitter4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.http.HttpParameter;
import junit.framework.TestCase;

public class QueryTest extends TestCase {
	
	public void testSinceParameterWithString() {
		Query q = new Query();
		q.setQuery("irrelevant");
		String date = "2010-01-20";
		q.setSince(date);

		HttpParameter[] params = q.asPostParameters();
		
		HttpParameter sinceParam = null;
		for (int i=0;i<params.length;i++) {
			HttpParameter param = params[i];
			if (param.getName().equals("since")){
				sinceParam = param;
			}
		}
		
		assertNotNull(sinceParam);
		assertEquals(date, sinceParam.getValue());
	}
	
	public void testSinceParameterWithDate() throws ParseException {
		Query q = new Query();
		q.setQuery("irrelevant");

		String date = "2010-01-20";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		q.setSince(df.parse(date));

		HttpParameter[] params = q.asPostParameters();
		
		HttpParameter sinceParam = null;
		for (int i=0;i<params.length;i++) {
			HttpParameter param = params[i];
			if (param.getName().equals("since")){
				sinceParam = param;
			}
		}
		
		assertNotNull(sinceParam);
		assertEquals(date, sinceParam.getValue());
	}
	
	public void testUntilParameterWithString() {
		Query q = new Query();
		q.setQuery("irrelevant");
		String date = "2010-01-20";
		q.setUntil(date);

		HttpParameter[] params = q.asPostParameters();
		
		HttpParameter untilParam = null;
		for (int i=0;i<params.length;i++) {
			HttpParameter param = params[i];
			if (param.getName().equals("until")){
				untilParam = param;
			}
		}
		
		assertNotNull(untilParam);
		assertEquals(date, untilParam.getValue());
	}
	
	public void testUntilParameterWithDate() throws ParseException {
		Query q = new Query();
		q.setQuery("irrelevant");
		String date = "2010-01-20";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		q.setUntil(df.parse(date));


		HttpParameter[] params = q.asPostParameters();
		
		HttpParameter untilParam = null;
		for (int i=0;i<params.length;i++) {
			HttpParameter param = params[i];
			if (param.getName().equals("until")){
				untilParam = param;
			}
		}
		
		assertNotNull(untilParam);
		assertEquals(date, untilParam.getValue());
	}

}
