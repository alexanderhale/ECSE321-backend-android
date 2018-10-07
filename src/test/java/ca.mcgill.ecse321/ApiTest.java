package ca.mcgill.ecse321;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ApiTest {

    @Test
    public void testSecureEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testDriverIsNull()throws IOException{
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    @Test
    public void testRiderIsNull(){
/*        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);*/
    }

    @Test
    public void testDriverLogin() throws Exception{

    }

    @Test
    public void testRegisterSuccessfully() throws Exception {

    }

    @Test
    public void getAllDriversSuccessfully() throws Exception{

    }

    @Test
    public void getAllRidersSuccessfully() throws Exception {

    }
}
