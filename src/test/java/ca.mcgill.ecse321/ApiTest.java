package ca.mcgill.ecse321;

import com.sun.tools.javac.util.DefinedBy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
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
    public void testDriverIsNull(){
/*        HttpEntity<Driver> entity = new HttpEntity<Driver>(null, headers);

        ResponseEntity<Driver> response = ;

        assertEquals(entity, false);*/
    }

    @Test
    public void testRiderIsNull(){

    }

    @Test
    public void testLoginSuccessully() throws Exception{
        //example:
/*        String username = "testDriver";
        String password = "password";
        String carModel = "honda";

        Driver driver = Api.createDriver(username, password, carModel);

        Driver returned = Api.loginDriver(driver);

        assertEquals(driver, returned, true);*/
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
