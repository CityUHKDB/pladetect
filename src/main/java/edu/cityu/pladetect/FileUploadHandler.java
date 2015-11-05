package edu.cityu.pladetect;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUploadHandler extends HttpServlet {
    private final String UPLOAD_DIR = "/home/dickson/Documents/FYP/fyp_upload/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        out.println("Hello Servlet");
        boolean isMulPart = ServletFileUpload.isMultipartContent(req);

        if (isMulPart)
            out.println("is mul part");
        else
            out.println("not mul part");

        if (!isMulPart) {
            // Not a file upload request
            // Send back error page
            out.println("No file has been chosen");
            return;
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String pathloc = "";


        try {
            FileItemIterator items = new ServletFileUpload(new DiskFileItemFactory()).getItemIterator(req);

            while (items.hasNext()) {
                FileItemStream f = items.next();
                pathloc = UPLOAD_DIR + System.currentTimeMillis() + "-" + f.getName();
                inputStream = f.openStream();
                outputStream = new FileOutputStream(new File(pathloc));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }
        } catch (Exception e) {
            out.println("Something went wrong");
            out.println(e.getMessage());
        } finally {
            if (inputStream != null)
                inputStream.close();

            if (outputStream != null)
                outputStream.close();

            out.println("File has been uploaded successfully");
            try {
                DataETL.getInstance().setPath(pathloc);
                DataETL.getInstance().setOut(out);
                DataETL.getInstance().readFile();
                DataETL.getInstance().disposePath();
                DataETL.getInstance().disposeWriter();
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }
}