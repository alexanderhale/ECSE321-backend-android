package ca.mcgill.ecse321;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

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
    public void testRiderIsNull() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void mediaTypeIsCorrectTest() throws Exception {
        String jSonType = "application/json";
        HttpUriRequest jSonRequest = new HttpGet("https://ecse321-project.herokuapp.com");
        HttpResponse jSonResponse = HttpClientBuilder.create().build().execute(jSonRequest);
        String type = ContentType.getOrDefault(jSonResponse.getEntity()).getMimeType();
        assertEquals (jSonType, type);
    }

//not sure how to test proper print for logging in, will probably scrap and focus on http methods
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//    private final PrintStream originalErr = System.out;
//    @Before
//    public void createStreams() {
//        //create the streams we'll use to test
//        System.setOut(new PrintStream(outContent));
//        System.setErr(new PrintStream(errContent));
//    }
//    @After
//    public void clearStreams() {
//        //clear for future usage
//        System.setOut(originalOut);
//        System.setErr(originalErr);
//    }
//    @Test
//    public void testDriverLogin() throws Exception {
//        //create a test user
//        //try to log into test user
//        //check if prints ok
//        //Driver testDriver = new Driver();
//
//    }
    @Test
    public void testDriverLogin_driverNotFound() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        System.out.println(testName);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    @Test
    public void testRiderLogin_driverNotFound() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        System.out.println(testName);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
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
