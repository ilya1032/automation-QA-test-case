import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.util.Properties;

public class FtpClient {

    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;
    private FTPClientConfig conf = new FTPClientConfig();

    public static FtpClient instance = null;

    public static synchronized FtpClient getInstance() {
        if (instance == null)
            instance = new FtpClient();
        return instance;
    }

    private FtpClient() {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/ftpclient.properties");
            property.load(fis);

            server = property.getProperty("ftp.server");
            port = Integer.parseInt(property.getProperty("ftp.port"));
            user = property.getProperty("ftp.user");
            password = property.getProperty("ftp.password");

        } catch (IOException e) {
            e.printStackTrace();
        }

        conf.setServerTimeZoneId("UTC");
    }

    void open() throws IOException {
        ftp = new FTPClient();
        ftp.configure(conf);

        // Purely for debug purposes
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
    }

    boolean login() throws IOException {
        if (!ftp.login(this.user, this.password)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        return true;
    }

    boolean login(String username, String password) throws IOException {
        if (!ftp.login(username, password)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        return true;
    }

    void close() throws IOException {
        ftp.disconnect();
    }

    boolean changeDirectory(String directory) throws IOException {
        return ftp.changeWorkingDirectory(directory);
    }

    FTPFile[] listFiles() throws IOException {
        return ftp.listFiles("");
    }

    String[] listFileNames() throws IOException {
        return ftp.listNames("");
    }

    void downloadFile(String source, String destination) throws IOException {
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        FileOutputStream out = new FileOutputStream(destination);
        ftp.retrieveFile(source, out);
        out.close();
        ftp.enterLocalActiveMode();
    }

    boolean putFileToPath(File file, String path) throws IOException {
//        ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        FileInputStream in = new FileInputStream(file);
        boolean success = ftp.storeFile(path, in);
        ftp.enterLocalActiveMode();
        in.close();
        return success;
    }
}
