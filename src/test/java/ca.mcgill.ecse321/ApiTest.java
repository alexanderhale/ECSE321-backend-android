package ca.mcgill.ecse321;

import ca.mcgill.ecse321.driver.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(Driver.class)
public class ApiTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private Driver someClass;  //this could be Autowired

    //, initialized in the test method
    //, or created in setup block

    @Test // test whether the application endpoint is secure
    public void testSecureEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test // test whether the application media type is JSON
    public void mediaTypeIsCorrectTest() throws Exception {
        String jSonType = "application/json";
        HttpUriRequest jSonRequest = new HttpGet("https://ecse321-project.herokuapp.com");
        HttpResponse jSonResponse = HttpClientBuilder.create().build().execute(jSonRequest);
        String type = ContentType.getOrDefault(jSonResponse.getEntity()).getMimeType();
        assertEquals(jSonType, type);
    }

    @Test // test what happens when you call a Driver without any other info
    public void testDriverIsNull() throws IOException {
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

    @Test // test that a Driver registration is rejected if the username is already in use
    public void testDriverRegisterDuplicate() throws Exception {

    }


    @Test // test that a Rider registration is rejected if the username is already in use
    public void testRiderRegisterDuplicate() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider/register" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_CONFLICT);
    }

    @Test // test that a Driver can be successfully registered when the parameters are correct
    public void testDriverRegisterSuccessfully() throws Exception {
        HttpUriRequest request = new HttpPost("https://ecse321-project.herokuapp.com/driver/login");


        StringEntity input = new StringEntity("{\"username\":\"ege\",\n" +
                "\t\"password\":\"pass\"\n" +
                "}");
        ((HttpPost) request).setEntity(input);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }


    @Test // test that a Driver login is rejected if the username is null
    public void testDriverLoginNullUser() throws Exception {
        HttpUriRequest request = new HttpPost("https://ecse321-project.herokuapp.com/driver/login");
        StringEntity input = new StringEntity("{\"username\":\"\",\n" +
                "\t\"password\":\"pass\"\n" +
                "}");
        ((HttpPost) request).setEntity(input);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
    }

    @Test // test that a Driver login is rejected if the password is null
    public void testDriverLoginNullPassword() throws Exception {
        HttpUriRequest request = new HttpPost("https://ecse321-project.herokuapp.com/driver/login");
        StringEntity input = new StringEntity("{\"username\":\"ege\",\n" +
                "\t\"password\":\"\"\n" +
                "}");
        ((HttpPost) request).setEntity(input);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
    }

    @Test // test what happens when you try to log in with a Driver that is not found
    public void testDriverLogin_driverNotFound() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        System.out.println(testName + "ss");
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Driver login is rejected if the password is wrong
    public void testDriverLoginWrongPassword() throws Exception {
        // TODO
    }

    @Test //test that a Rider login is rejected if the username is null
    public void testRiderLoginNullUser() throws Exception {
        String testName = "";
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider/login" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test   // test that a Rider login is rejected if the password is null
    public void testRiderLoginNullPassword() throws Exception {
        // TODO
    }

    @Test   // test that a Rider login is rejected if the password is wrong
    public void testRiderLoginWrongPassword() throws Exception {
        // TODO
    }

    @Test // test what happens when you try to log in with a Rider that is not found
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
    public void updateCarModelSuccessfully() throws Exception {
        // TODO
    }
    // TODO we also need a test for when the car model isn't updated properly
    // but there's no error checking in the Controller so that needs to be added first

    @Test   // test that all Drivers are returned properly
    public void getAllDriversSuccessfully() throws Exception {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/driver/secure/all");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test // test that all Riders are returned properly
    public void getAllRidersSuccessfully() throws Exception {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/rider/secure/all");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test // test what happens when you call a Journey without any other info
    public void testJourneyIsNull() throws IOException {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test   // test that a Journey can be created successfully
    public void createJourneySuccessfully() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(7);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey/create" + testName);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
    }

    @Test   // test that all Journeys are returned properly
    public void getAllJourneysSuccessfully() throws Exception {
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey/all");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    //TODO: double checking on range
    @Test   // test that a Journey's associated Driver is returned properly
    public void getDriverUsingJourneySuccessfully() throws Exception {
        long testJourneyID = new RandomUtils().nextLong(0, 100);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey/" + testJourneyID + "/driver");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    //TODO: double checking on range
    @Test   // test that a Rider is successfully added to a Journey
    public void addRiderToJourneySuccessfully() throws Exception {
        long testJourneyID = new RandomUtils().nextLong(0, 100);
        long testRiderID = new RandomUtils().nextLong(0, 100);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey/" + testJourneyID + "/addRider" + testRiderID);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test   // test that a Journey's properties are modified successfully
    public void modifyJourneySuccessfully() throws Exception {
        long testJourneyID = new RandomUtils().nextLong(0, 100);
        HttpUriRequest request = new HttpGet("https://ecse321-project.herokuapp.com/journey/" + testJourneyID + "/modify");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }
}
