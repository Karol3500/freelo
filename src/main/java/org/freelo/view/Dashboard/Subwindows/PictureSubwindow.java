package org.freelo.view.Dashboard.Subwindows;

import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.files.PictureUploader;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

/**
 * Created by Adrian on 2015-01-13.
 */
public class PictureSubwindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    public PictureSubwindow() {
        super("Upload Picture");
        center();
        setClosable(true);
        setResizable(false);
        VerticalLayout main = new VerticalLayout();
        main.addStyleName("picturepopup");
        main.setSizeFull();
        setContent(main);
        setHeight("150px");
        setWidth("500px");

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, true, true, true));
        setContent(content);

        content.addComponent(buildPictureField());
    }

    private Component buildPictureField() {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        //root.setMargin(true);
        root.addStyleName("picture-form");

        PictureUploader pictureUploader = new PictureUploader();
        Upload uploadPicture = new Upload("Please provide picture file:", pictureUploader);
        uploadPicture.setButtonCaption("Upload");
        uploadPicture.addSucceededListener(pictureUploader);
        uploadPicture.addSucceededListener(new UploadSuccededListener());

        root.addComponent(uploadPicture);
        root.setComponentAlignment(uploadPicture, Alignment.MIDDLE_CENTER);
        return root;
    }

    class UploadSuccededListener implements Upload.SucceededListener {

        @Override
        public void uploadSucceeded(Upload.SucceededEvent event) {
            close();
        }
    }


}
