package org.freelo.model.files.dao;

import org.freelo.model.files.dbFile;
import org.freelo.model.files.multipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Adrian on 12-11-2014.
 */
public class FilesService {

    //@Autowired
    //private FileRepository fileRepository;

    public dbFile save(multipartFile mfile)
    {
        dbFile fileToSave = new dbFile();

        fileToSave.setFileName(mfile.getFile().getOriginalFilename());
        fileToSave.setUserId(5);
        File dir = new File("user_files" + File.separator + Integer.toString(fileToSave.getUserId()));
        if(!dir.exists())
            dir.mkdirs();
        fileToSave.setFilePath(dir.getAbsolutePath()+ File.separator + fileToSave.getFileName());
        fileToSave.setFileSize(mfile.getFile().getSize());
        //System.out.println(mfile.getFile().getOriginalFilename());
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(new File(fileToSave.getFilePath()));
            outputStream.write(mfile.getFile().getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error while saving file " + fileToSave.getFileName());
            e.printStackTrace();
        }

        return fileToSave;
    }

   // public void delete(int fileID, int userID)
    public void delete(String filePath)
    {
        System.out.println(filePath);
        File file = new File(filePath);

        try {

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
