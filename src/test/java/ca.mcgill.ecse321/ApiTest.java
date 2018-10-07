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

    @Test // test whether the application endpoint is secure
    public void testSecureEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test  // test whether the application media type is JSON
    public void mediaTypeIsCorrectTest() throws Exception {
        String jSonType = "application/json";
        HttpUriRequest jSonRequest = new HttpGet("https://ecse321-project.herokuapp.com");
        HttpResponse jSonResponse = HttpClientBuilder.create().build().execute(jSonRequest);
        String type = ContentType.getOrDefault(jSonResponse.getEntity()).getMimeType();
        assertEquals (jSonType, type);
    }

    @Test // test what happens when you call a Driver without any other info
    public void testDriverIsNull()throws IOException{
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test // test what happens when you call a Rider without any other info
    public void testRiderIsNull() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Driver registration is rejected if the username is already in use
    public void testDriverRegisterDuplicate() throws Exception {
        // TODO
    }

    @Test   // test that a Rider registration is rejected if the username is already in use
    public void testRiderRegisterDuplicate() throws Exception {
        // TODO
    }

    @Test   // test that a Driver can be successfully registered when the parameters are correct
    public void testDriverRegisterSuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that a Rider can be successfully registered when the parameters are correct
    public void testRiderRegisterSuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that a Driver login is rejected if the username is null
    public void testDriverLoginNullUser() throws Exception {
        // TODO
    }

    @Test   // test that a Driver login is rejected if the password is null
    public void testDriverLoginNullPassword() throws Exception {
        // TODO
    }

    @Test   // test what happens when you try to log in with a Driver that is not found
    public void testDriverLogin_driverNotFound() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        System.out.println(testName);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Driver login is rejected if the password is wrong
    public void testDriverLoginWrongPassword() throws Exception {
        // TODO
    }

    @Test   // test that a Rider login is rejected if the username is null
    public void testRiderLoginNullUser() throws Exception {
        // TODO
    }

    @Test   // test that a Rider login is rejected if the password is null
    public void testRiderLoginNullPassword() throws Exception {
        // TODO
    }

    @Test   // test that a Rider login is rejected if the password is wrong
    public void testRiderLoginWrongPassword() throws Exception {
        // TODO
    }

    @Test   // test what happens when you try to log in with a Rider that is not found
    public void testRiderLogin_driverNotFound() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        System.out.println(testName);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Driver can be successfully logged in when the parameters are correct
    public void testDriverLoginSuccess() throws Exception {
        // TODO
    }

    @Test   // test that a Rider can be successfully logged in when the parameters are correct
    public void testRiderLoginSuccess() throws Exception {
        // TODO
    }

    @Test   // test that a Driver's car model is updated properly
    public void updateCarModelSuccessfully() throws Exception{
        // TODO
    }
    // TODO we also need a test for when the car model isn't updated properly
        // but there's no error checking in the Controller so that needs to be added first

    @Test   // test that all Drivers are returned properly
    public void getAllDriversSuccessfully() throws Exception{
        // TODO
    }

    @Test   // test that all Riders are returned properly
    public void getAllRidersSuccessfully() throws Exception {
        // TODO
    }

    @Test // test what happens when you call a Journey without any other info
    public void testJourneyIsNull()throws IOException{
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Journey can be created successfully
    public void createJourneySuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that all Journeys are returned properly
    public void getAllJourneysSuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that a Journey's associated Driver is returned properly
    public void getDriverUsingJourneySuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that a Rider is successfully added to a Journey
    public void addRiderToJourneySuccessfully() throws Exception {
        // TODO
    }

    @Test   // test that a Journey's properties are modified successfully
    public void modifyJourneySuccessfully() throws Exception {
        // TODO
    }
}
