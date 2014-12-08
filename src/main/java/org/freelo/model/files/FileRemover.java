package org.freelo.model.files;

import com.vaadin.server.StreamResource;

import java.io.File;

/**
 * Created by Adrian on 08-12-2014.
 */
public class FileRemover {
    private File fileToDelete;
    private UserFile userFile = new UserFile();
    private FileDAO fileDAO = new FileDAO();

    private boolean deleteFile(int ID){
        userFile = fileDAO.getFile(ID);
        String filePath = userFile.get_filePath();

        System.out.println(filePath);
        fileToDelete = new File(filePath);
        try {
            if(fileToDelete.delete()){
                System.out.println(fileToDelete.getName() + " is deleted!");
                return true;
            }else{
                System.out.println("Delete operation is failed.");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
