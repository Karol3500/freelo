package org.freelo.model.files;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Notification;

import java.io.File;

/**
 * Created by Adrian on 03-01-2015.
 */
public class FileManagement {

    UserFile userFile = new UserFile();
    FileDAO fileDAO = new FileDAO();

    public boolean uploadFile(String userName, File file){

        if(file.length() != 0){

            userFile.set_fileName(file.getName());
            userFile.set_filePath(file.getPath());
            userFile.set_fileSize(file.length());
            userFile.set_userName(userName);

            fileDAO.saveFile(userFile);

            showNotif("File: " + file.getName() + " uploaded successfully.");
            return true;
        }

        showNotif("File: " + file.getName() + " uploading failed.");
        return false;

    }

    public FileDownloader downloadFile(long ID){
        FileDownloader downloader = null;
        userFile = fileDAO.getFile(ID);

        try {
            Resource resource = new FileResource(new File(userFile.get_filePath()));
            downloader = new com.vaadin.server.FileDownloader(resource);
            return downloader;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return downloader;
    }

    public boolean deleteFile(long ID){
        File fileToDelete;
        userFile = fileDAO.getFile(ID);
        String filePath = userFile.get_filePath();
        fileToDelete = new File(filePath);

        try {
            fileDAO.deleteFile(ID);
            if(fileToDelete.delete()){
                showNotif("File: " + fileToDelete.getName() + " is deleted.");
                return true;
            }else{
                showNotif("Delete operation is failed.");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void showNotif(String message){

        Notification notif = new Notification(message);
        notif.setDelayMsec(1500);
        notif.show(Page.getCurrent());
    }

}
