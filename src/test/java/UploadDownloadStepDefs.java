import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

public class UploadDownloadStepDefs {

    private boolean isFileUploadedSuccessfully = false;
    private String[] fileNames;

    // client initialization; can be changed to "Before" hook
    private FtpClient client = FtpClient.getInstance();

    @When("client downloads {word} file")
    public void clientDownloadsFile(String fileName) throws IOException {
        client.downloadFile("/" + fileName, "./" + fileName);
    }

    @When("client uploads {word} to server")
    public void clientUploadsFileToServer(String fileName) throws IOException {
        File file = new File(fileName);
        isFileUploadedSuccessfully = client.putFileToPath(file, "ilyal" + fileName);
    }

    @Then("file {word} upload completes properly")
    public void fileUploadCompletesProperly(String fileName) throws IOException {
        assertTrue(isFileUploadedSuccessfully);
        File f = new File("./" + fileName);
        if (!f.delete())
            fail("Error deleting file");
    }

    @Then("file {word} being deleted from upload folder by server")
    public void fileDeletedByServer(String fileName) throws IOException {
        fileNames = client.listFileNames();
        boolean isFileFound = false;
        for (String file : fileNames)
            if (fileName.equals(file)) {
                isFileFound = true;
                break;
            }
        assertFalse(isFileFound);
    }

    @Then("file {word} download completes properly")
    public void fileDownloadCompletesProperly(String fileName) throws IOException {
        File f = new File("./" + fileName);
        assertTrue(f.exists());
        assertFalse(f.isDirectory());
        if (!f.delete())
            fail("Error deleting file");
    }

}
