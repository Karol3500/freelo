package org.freelo.model.files;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.freelo.model.users.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Adrian on 08-12-2014.
 */
public class PictureUploader implements Upload.Receiver, Upload.SucceededListener {
    File pictureToUpload;
    FileManagement fileManagement = new FileManagement();
    String userName=null;
    User user = new User();

    public OutputStream receiveUpload(String filename, String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            user = (User) VaadinSession.getCurrent().getAttribute("userClass");
            userName = user.getEmail();
            String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
            File dir = new File(basepath + File.separator + "WEB-INF" + File.separator + "images" + File.separator + userName);
            if(!dir.exists())
                dir.mkdirs();

            // Open the file for writing.
            pictureToUpload = new File(dir.getPath() + File.separator + filename);
            fos = new FileOutputStream(pictureToUpload);

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
        fileManagement.updatePicture(user, pictureToUpload);

    }

}
