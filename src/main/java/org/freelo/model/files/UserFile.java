package org.freelo.model.files;

import org.freelo.model.users.User;

import javax.persistence.*;

/**
 * Created by Adrian on 2014-12-08.
 */

@Entity
@Table
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    long _ID;

    //@OneToOne
    //private User _user;

    @Column
    private String _fileName;

    @Column
    private String _filePath;

    @Column
    private Long _fileSize;

    @Column
    private String _userName;

    public UserFile(){
        _fileName = "";
        _filePath = "";
        _fileSize = null;
        _userName = "";
    }

    public Long get_fileSize() {
        return _fileSize;
    }

    public void set_fileSize(Long _fileSize) {
        this._fileSize = _fileSize;
    }

    public String get_fileName() {
        return _fileName;
    }

    public void set_fileName(String _fileName) {
        this._fileName = _fileName;
    }

    public String get_filePath() {
        return _filePath;
    }

    public void set_filePath(String _filePath) {
        this._filePath = _filePath;
    }

    /*
    public User get_user() {
        return _user;
    }

    public void set_user(User _user) {
        this._user = _user;
    }*/

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }
}
