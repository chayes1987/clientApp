import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FTPClient{
    private String host_name;
    private int port_number;
    private FTPClientHelper helper;

    public FTPClient(String host_name, int port_number) {
        this.host_name = host_name;
        this.port_number = port_number;
    }

    public boolean uploadFile(File file, String username){
        String path = file.getPath();
        String file_contents;
        String filename = path.substring(path.lastIndexOf("\\") + 1);

        try{
            file_contents = new String(Files.readAllBytes(Paths.get(path)));
            return helper.sendRequest("UPL###" + username + "###" + filename + "###" + file_contents).equals("OK");
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
	}

    public boolean login(String username) {
        try {
            return helper.sendRequest("LOG###" + username).equals("OK");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout(String username) {
        try {
            return helper.sendRequest("OUT###" + username).equals("OK");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean connect() {
        try {
            helper = new FTPClientHelper(host_name, port_number);
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	public String downloadFile(String username, String filename) {
        String fileContents;

        try {
            fileContents = helper.sendRequest("DOW###" + username + "###" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (fileContents.isEmpty()){
            return "";
        }
        return fileContents;
	}

    public List<String> getListFiles(String username){
        List<String> list = new ArrayList<String>();
        File directory = new File("C:\\" + username);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            list.add(file.getName());
        }
        return list;
    }

    public boolean saveFile(String path, String fileContents) {
        byte[] file = fileContents.getBytes();
        try {
            Files.write(Paths.get(path), file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
