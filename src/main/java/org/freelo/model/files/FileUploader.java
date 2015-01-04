package org.freelo.model.files;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.freelo.view.tasks.TaskPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Adrian on 08-12-2014.
 */
public class FileUploader implements Upload.Receiver, Upload.SucceededListener {
    File fileToUpload;
    FileManagement fileManagement = new FileManagement();
    String userName=null;
    private TaskPage taskPage;

    public OutputStream receiveUpload(String filename, String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            userName = (String) VaadinSession.getCurrent().getAttribute("user");
            File dir = new File("user_files" + File.separator + userName);
            if(!dir.exists())
                dir.mkdirs();

            // Open the file for writing.
            fileToUpload = new File(dir.getAbsolutePath() + File.separator + filename);
            fos = new FileOutputStream(fileToUpload);

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
        fileManagement.uploadFile(userName, fileToUpload);

    }

}
