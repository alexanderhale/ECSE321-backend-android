package ca.mcgill.ecse321;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ApiTest {

    @Test
    public void testSecureEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/secure/users");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
