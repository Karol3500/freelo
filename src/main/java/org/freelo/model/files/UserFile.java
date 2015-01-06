package org.freelo.model.files;

import javax.persistence.*;

/**
 * Created by Adrian on 2014-12-08.
 */

@Entity
@Table
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    long id;

    //@OneToOne
    //private User _user;

    @Column
    private String fileName;

    @Column
    private String filePath;

    @Column
    private Long fileSize;

    @Column
    private String userName;

    public UserFile(){
        fileName = "";
        filePath = "";
        fileSize = null;
        userName = "";
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /*
    public User get_user() {
        return _user;
    }

    public void set_user(User _user) {
        this._user = _user;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
