package org.freelo.model.files;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

import java.io.File;

/**
 * Created by Adrian on 08-12-2014.
 */
public class FileDownload {
    private File fileToDownload;
    private UserFile userFile = new UserFile();
    private FileDAO fileDAO = new FileDAO();

    public FileDownloader downloadFile(int ID){
        FileDownloader downloader = null;
        //userFile = fileDAO.getFile(ID);
        //String filePath = userFile.get_filePath();
        String filePath = "C:\\a.txt";

        //System.out.println(filePath);
        try {
            Resource resource = new FileResource(new File(filePath));
            downloader = new com.vaadin.server.FileDownloader(resource);
            return downloader;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return downloader;
    }

}
