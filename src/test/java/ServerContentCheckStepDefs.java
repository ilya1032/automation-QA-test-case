import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ServerContentCheckStepDefs {


    // client should be in static content for scenario, not singleton
    private FtpClient client = FtpClient.getInstance();
    private FTPFile[] files;
    private String[] fileNames;

    @Given("client connects to ftp server")
    public void clientConnectsToFtpServer() throws IOException {
        client.open();
    }

    @And("client logins anonymously")
    public void clientLoginsAnonymously() throws IOException {
        client.login();
    }

    @When("client get list of file and directory names")
    public void clientsGetListOfFileNames() throws IOException {
        fileNames = client.listFileNames();
    }

    @When("client get list of files")
    public void clientsGetListOfFiles() throws IOException {
        files = client.listFiles();
    }

    @When("client changes directory to upload")
    public void changeDirToUpload() throws IOException {
        client.changeDirectory("upload");
    }

    @Then("the file/folder {word} exists")
    public void fileExists(String fileName) throws IOException {
        boolean isFileFound = false;
        for (String file : fileNames)
            if (fileName.equals(file)) {
                isFileFound = true;
                break;
            }
        assertTrue(isFileFound);
    }

    @Then("client can change directory to upload")
    public void changeToUploadDirectory() throws IOException {
        assertTrue(client.changeDirectory("upload"));
    }

    @Then("current directory is empty")
    public void directoryIsEmpty() throws IOException {
        fileNames = client.listFileNames();
        assertEquals(0, fileNames.length);
    }

    @Then("the file {word} is size of {int} and last modified on {word} {word}")
    public void checkFileInfo(String fileName, int fileSizeInKb, String date, String time) {

        long fileSize = (long)fileSizeInKb * 1024;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date formattedDate = null;
        try {
            formattedDate = sdf.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(FTPFile file : files) {
            if (file.getName().equals(fileName)) {
                assertEquals(fileSize, file.getSize());
                assertEquals(formattedDate, file.getTimestamp().getTime());
            }
        }
    }
}
