package org.freelo.controller.files;

import org.apache.commons.io.IOUtils;
import org.freelo.model.files.FileRepository;
import org.freelo.model.files.dao.FilesService;
import org.freelo.model.files.dbFile;
import org.freelo.model.files.multipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/files")
public class FileController {

    private FilesService filesService = new FilesService();
    dbFile fileFromDatabase = new dbFile();
    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        model.addAttribute("file", new multipartFile());
        model.addAttribute("files", fileRepository.findAll());
        return "files";

    }

    @RequestMapping(value = "/add", method= RequestMethod.POST)
    public String uploadFile(@ModelAttribute("file") multipartFile mfile, BindingResult result){
        Map<Object, Object> model = new HashMap<>();
        if (mfile.getFile().isEmpty())
        {
            System.out.println("Error. No file selected.");
            return "redirect:/files";
        }
        for (dbFile file : fileRepository.findAll()) {
            if (mfile.getFile().getOriginalFilename().equals(file.getFileName())) {
                System.out.println("Error. File already exists.");
                return "redirect:/files";
            }
        }
        if(!result.hasErrors()){
            //System.out.println(mfile.getFile().getOriginalFilename());
            fileFromDatabase = this.filesService.save(mfile);
            fileRepository.save(fileFromDatabase);
            return "redirect:/files";

        } else{
            System.out.println("Error:" + result.toString());
            return "redirect:/files";
        }
    }

    @RequestMapping("/delete/{fileId}")
    public String deleteUser(@PathVariable("fileId") Long fileId) {
        fileFromDatabase = fileRepository.findOne(fileId);

        filesService.delete(fileFromDatabase.getFilePath());
        fileRepository.delete(fileFromDatabase);
        return "redirect:/files";
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.POST)
    public String getFile(@PathVariable("fileId") Long fileId, HttpServletResponse response) throws Exception{
        OutputStream outputStream = response.getOutputStream();

        try{
            fileFromDatabase = fileRepository.findOne(fileId);
            File fileToDownload = new File(fileFromDatabase.getFilePath());
            InputStream inputStream = new FileInputStream(fileToDownload);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileFromDatabase.getFileName());
            response.setHeader(headerKey, headerValue);
            IOUtils.copy(inputStream, outputStream);

            inputStream.close();

        }catch(Exception e){
            System.out.println("Download fail. " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            response.flushBuffer();
            outputStream.close();
        }
        return "redirect:/files";
    }
}