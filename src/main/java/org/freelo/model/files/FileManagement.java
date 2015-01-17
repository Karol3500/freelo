package org.freelo.model.files;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Notification;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

import java.io.File;

/**
 * Created by Adrian on 03-01-2015.
 */
public class FileManagement {

    UserFile userFile = new UserFile();
    FileDAO fileDAO = new FileDAO();
    UserManagement um = new UserManagement();

    public boolean updatePicture (User user, File file){

        if(file.length() != 0){

            user.setPicturePath(file.getPath());
            um.userUpdate(user);

            showNotif("Picture: " + file.getName() + " updated successfully.");
            return true;
        }

        showNotif("Picture: " + file.getName() + " update failed.");
        return false;

    }

    public boolean uploadFile(String userName, File file){

        if(file.length() != 0){

            userFile.setFileName(file.getName());
            userFile.setFilePath(file.getPath());
            userFile.setFileSize(file.length());
            userFile.setUserName(userName);

            FileDAO.saveFile(userFile);

            showNotif("File: " + file.getName() + " uploaded successfully.");
            return true;
        }

        showNotif("File: " + file.getName() + " uploading failed.");
        return false;

    }

    public FileDownloader downloadFile(long ID){
        FileDownloader downloader = null;
        userFile = FileDAO.getFile(ID);

        try {
            Resource resource = new FileResource(new File(userFile.getFilePath()));
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
        userFile = FileDAO.getFile(ID);
        String filePath = userFile.getFilePath();
        fileToDelete = new File(filePath);

        try {
            FileDAO.deleteFile(ID);
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
