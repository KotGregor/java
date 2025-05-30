/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Юля
 */
@WebServlet(urlPatterns = {"/FramesServlet/*"})
public class FramesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            renderFrameset(out, contextPath);            
        } else if (pathInfo.equals("/up_window")) {
            renderUpWindow(request, response, contextPath, out);
        } else if (pathInfo.equals("/down_window")) {
            out.println("<html><body><h1>No Information</h1></body></html>");
        } else if (pathInfo.equals("/down_frame_content")) {
            renderDownFrameContent(request, out, response);
        return;
            //            Map<String, String> params = getParameters(request);
//            String letter = params.getOrDefault("letter", "");

//            out.printf("<html><body><center>'%s'</center></body></html>", letter);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Path not found: " + pathInfo);
        }
    }

    private Map<String, String> getParameters(HttpServletRequest request) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        String query = request.getQueryString();

        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                parameters.put(key, value);
            }
        }

        return parameters;
    };
    
    private void renderFrameset(PrintWriter out, String contextPath){
        out.println("<html><frameset rows='50%,50%'>");
        out.println("<frame name='up_window' src='" + contextPath + "/FramesServlet/up_window'>");
        out.println("<frame name='down_window' src='" + contextPath + "/FramesServlet/down_window'>");
        out.println("</frameset></html>"); 
    }

    private String getSessionData(HttpServletRequest request){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String major = request.getParameter("major");
        String studyForm = request.getParameter("studyForm");
        String[] literGenre = request.getParameterValues("litGenre");
        String[] musicGenre = request.getParameterValues("musicGenre");
        StringBuilder currData = new StringBuilder();
        
        if (firstName != null && lastName != null) {
        
        currData.append("First name: ")
                .append(HTMLFilter.filter(firstName))
                .append("<br>");
        
        currData.append("Last name: ")
                .append(HTMLFilter.filter(lastName))
                .append("<br>");
        
        currData.append("Faculty: ")
                .append(HTMLFilter.filter(major))
                .append("<br>");//
        
        currData.append("Form of education: ")
                .append(studyForm != null ? 
                        HTMLFilter.filter(studyForm) :
                        "not specified")
                .append("<br>");
        
        currData.append("Favoutite genre of book: ");
        currData.append(literGenre != null ? 
                        HTMLFilter.filter(String.join(", ", literGenre)):
                        "not specified")
                .append("<br>");
        
        currData.append("Favoutite genre of music: ");
        currData.append(musicGenre != null ?
                        HTMLFilter.filter(String.join(", ", musicGenre)):
                        "not specified")
                .append("<br>");
        

    }
        return currData.toString();
}
    
    private void renderUpWindow(
            HttpServletRequest request, HttpServletResponse response,
            String contextPath, PrintWriter out
    ){            
//        out.println("<!DOCTYPE html>");
        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Обработка параметров запроса</title>");
//        out.println("</head>");
        out.println("<body>");
        
        out.println("<form method='post' action='" + contextPath + "/FramesServlet/down_frame_content' target='down_window'>");

        out.println("First name: <input type='text' name='firstName' required><br>");
        out.println("Last name: <input type='text' name='lastName' required><br>");
      
        out.println("<br>Faculty: <select name='major'>");
        out.println("<option value='Philosophy'>Philosophy</option>");
        out.println("<option value='Philology'>Philology</option>");
        out.println("<option value='Medicine'>Medicine</option>"); 
        out.println("<option value='History'>History</option>");
        out.println("<option value='Economics'>Economics</option>");
        out.println("<option value='Law'>Law</option>");
        out.println("<option value='Physics and Mathematics'>Physics and Mathematics</option>");
        out.println("</select><br>");
        
        out.println("<br>Form of education:<br>");
        out.println("<input type='radio' name='studyForm' value='Full-time'>Full-time<br>");
        out.println("<input type='radio' name='studyForm' value='Extramural'>Extramural<br>");
        out.println("<input type='radio' name='studyForm' value='Part-time'>Part-time<br>");
      
        out.println("<br>Favorite genre of book :<br>");
        out.println("<input type='checkbox' name='litGenre' value='Fantasy'>Fantasy<br>");
        out.println("<input type='checkbox' name='litGenre' value='Horror'>Horror<br>");
        out.println("<input type='checkbox' name='litGenre' value='Science-fintion'>Science-fiction<br>");
        out.println("<input type='checkbox' name='litGenre' value='Adventure'>Adventure<br>");
        
        out.println("<br>Favorite genre of music :<br>");
        out.println("<input type='checkbox' name='musicGenre' value='Rock'>Rock<br>");
        out.println("<input type='checkbox' name='musicGenre' value='Blues'>Blues<br>");
        out.println("<input type='checkbox' name='musicGenre' value='Punk'>Punk<br>");
        out.println("<input type='checkbox' name='musicGenre' value='Classic'>Classic<br>");
        
        out.println("<br><input type='submit' value='Отправить'>");
                
        out.println("</form>");   
        
//        String currData = getSessionData(request);
//        data.add(currData.toString() + "<hr>");
//        session.setAttribute("data", data);
//        out.println(data);
            
        out.println("</body>");
        out.println("</html>");
    }
    private void renderDownFrameContent(HttpServletRequest request, PrintWriter out, HttpServletResponse response)
    throws IOException{
        HttpSession session = request.getSession();
        List<String> data = (List<String>) session.getAttribute("data");
        if (data == null) {
            data = new ArrayList<>();
        }

        String currData = getSessionData(request);
        if (!currData.isEmpty()) {
            data.add(currData + "<hr>");
            session.setAttribute("data", data);
        }
        
//        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
//        out.println("Data in session: " + data);

        if(data.isEmpty()){
            out.println("<h3>Нет данных для отображения.</h3>");
        }
        else{
            for (String str : data) {
                out.println(str);
            }
        }
        
        out.println("</body>");
        out.println("</html>");
//        response.sendRedirect(request.getContextPath() + "/FramesServlet/up_window");

    }
}
