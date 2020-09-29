import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConnectionStepDefs {

    private FtpClient client = FtpClient.getInstance();
    private String login;
    private String password;
    private String result = "True";

    @Given("client has {string} and {string}")
    public void setUpLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @When("client tries to connect")
    public void clientConnectsToServer() {

        try {
            client.open();
            client.login(login, password);
        } catch (IOException ex) {
            result = "False";
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                result = "False";
            }
        }
    }

    @Then("result should be {string}")
    public void assertConnectionResult(String result) {
        assertEquals(result, this.result);
    }
}

