package web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import utils.URILookup;

@ManagedBean
@SessionScoped
public class UploadManager implements Serializable {

    UploadedFile file;
    
    String completePathFile;
    String pathFile;
    String filename;

    public UploadManager() {
    }

    public void upload(String context, int id, String oldCompleteFilePath) {
        if (file != null) {
            try {
                // se já tiver um ficheiro associado, vai eliminá-lo antes de adicionar um novo
                if (oldCompleteFilePath != null && oldCompleteFilePath.trim().length() > 0) {
                    Path oldpath = FileSystems.getDefault().getPath(oldCompleteFilePath);
                    Files.delete(oldpath);
                }
                
                filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
                
                pathFile = URILookup.getServerDocumentsFolder() + context + "/" + id + "/";
                
                File folder = new File(pathFile);
                folder.mkdirs();
                
                completePathFile = pathFile + filename;
                
                InputStream in = file.getInputstream();
                FileOutputStream out = new FileOutputStream(completePathFile);

                byte[] b = new byte[1024];
                int readBytes = in.read(b);

                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }

                in.close();
                out.close();

                FacesMessage message = new FacesMessage("File: " + file.getFileName() + " uploaded successfully!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch(NoSuchFileException | DirectoryNotEmptyException ex) {
                FacesMessage message = new FacesMessage("ERROR :: Deleting: " + ex.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERROR :: File: " + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCompletePathFile() {
        return completePathFile;
    }

    public void setCompletePathFile(String completePathFile) {
        this.completePathFile = completePathFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
    
    
}
