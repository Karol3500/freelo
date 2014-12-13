package org.freelo.model.files;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Adrian on 08-12-2014.
 */
public class FileUploader implements Upload.Receiver, Upload.SucceededListener {
    private File fileToUpload;
    private UserFile userFile = new UserFile();
    private FileDAO fileDAO = new FileDAO();

    public OutputStream receiveUpload(String filename, String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // creating a folder to store the file
            //System.out.println(fileToUpload.getName() + "\n" + mimeType);
            //File dir = new File("user_files" + File.separator + Integer.toString(userFile.get_user().getId()));
            File dir = new File("user_files");
            if(!dir.exists())
                dir.mkdirs();

            // Open the file for writing.
            fileToUpload = new File(dir.getAbsolutePath() + File.separator + filename);
            fos = new FileOutputStream(fileToUpload);
            userFile.set_fileName(filename);
            userFile.set_filePath(dir.getAbsolutePath() + File.separator + filename);
            userFile.set_fileSize(fileToUpload.length());
            fileDAO.saveFile(userFile);


        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        } catch (Exception e) {
            new Notification("Problem while saving the file",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

    public void uploadSucceeded(Upload.SucceededEvent event) {
    }
}
