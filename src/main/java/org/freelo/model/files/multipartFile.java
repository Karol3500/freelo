package org.freelo.model.files;

/**
 * Created by Adrian on 15-11-2014.
 */
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class multipartFile {

    CommonsMultipartFile file;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

}
