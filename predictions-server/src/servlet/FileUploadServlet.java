package servlet;



import engine.per.file.engine.api.SystemEngineAccess;
import engine.per.file.jaxb2.generated.PRDWorld;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import utils.ServletUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet("/upload-file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        Collection<Part> parts = request.getParts();

        for (Part part : parts) {
            if (part.getContentType() != null && part.getContentType().startsWith("text/xml")) {
                PRDWorld prdWorld = null;
                try {
                    prdWorld =deserializeFrom(part.getInputStream());
                    String simulationName=prdWorld.getName();
                    if(ServletUtils.getSEInstanceBySimulationName(getServletContext(),simulationName)==null){ //the simulation not exist
                        ServletUtils.addSEInstanceBySimulationName(getServletContext(),simulationName,ServletUtils.initEngineAttributeName(getServletContext()));
                        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName(
                                getServletContext(), simulationName);
                        systemEngineAccess.fromFileToSE(prdWorld);
                    }
                    else
                        throw new RuntimeException("the simulation "+simulationName+" already exist in the system");
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static PRDWorld deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PRDWorld.class);
        return (PRDWorld) jc.createUnmarshaller().unmarshal(inputStream);
    }



}
