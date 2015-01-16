package org.freelo.controller.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.freelo.model.files.UserFile;

public class FileController {

    private UserFile fileFromDatabase = new UserFile();
    public boolean deleteFile(Long fileId) {

        //filesService.delete(fileFromDatabase.get_filePath());
        //fileRepository.delete(fileFromDatabase);
        return true;
    }

    public File getFile() {

        File fileToDownload = null;

        try{
            fileToDownload = new File(fileFromDatabase.getFilePath());
            InputStream inputStream = new FileInputStream(fileToDownload);
            //String headerKey = "Content-Disposition";
            //String headerValue = String.format("attachment; filename=\"%s\"", fileFromDatabase.getFileName());


            inputStream.close();

        }catch(Exception e){
            System.out.println("Download fail. " + e.getMessage());
            e.printStackTrace();
        }
        finally {

        }
        return fileToDownload;
    }
}