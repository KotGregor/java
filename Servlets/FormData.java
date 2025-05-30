/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

//Разработать сервлет отвечающий следующим требованиям:
//При запуске без параметров (например, путем ввода в адресную строку браузера
//сервлет должен выдавать страничку с произвольной формой. 
//Форма должна содержать не менее трех интерфейсных элементов из разных групп
//(например: текстовое поле, чекбоксы, список выбора)
//При отправке заполненной формы должен выдать страничку с той же формой, 
//ниже которой на страничке должна быть выведена информация ранее введенная в форму.
//При каждой следующей отправке данных формы, сервлет должен добавлять
//их ниже ранее введенных на страничке с формой.
//В данной работе нельзя пользоваться файлом или иным приемом(сессии) 
//для хранения данных между запросами, можно использовать только скрытые поля.

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Юля
 */
@WebServlet(urlPatterns = {"/FormData"})
public class FormData extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //записывать как текст, а не как байтовое значение
        PrintWriter out = response.getWriter();
        
        String prevData = request.getParameter("history");
        if (prevData == null) prevData = "";

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String major = request.getParameter("major");
        String studyForm = request.getParameter("studyForm");
        String[] literGenre = request.getParameterValues("litGenre");
        String[] musicGenre = request.getParameterValues("musicGenre");
        
        if (firstName != null && lastName != null) {
            StringBuilder currData = new StringBuilder();
        
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
        prevData += "<hr>";
        prevData += currData.toString(); 
        }
        
       
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Обработка параметров запроса</title>");
        out.println("</head>");
        out.println("<body>");
        
        out.print("<form method='post'>");
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
        
        //
        out.println("<input type='hidden' name='history' value='" + prevData + "'>");
        
        out.println("<br><input type='submit' value='Отправить'>");
                
        out.println("</form>");
        
        out.println(prevData); 
        
        out.println("</body>");
        out.println("</html>");
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
}

final class HTMLFilter {
   public static String filter(String message) {
      if (message == null) return null;
      return message
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("&", "&amp;")
            .replace("\"", "&quot;");
    }
}


//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String major = request.getParameter("major");
//        String studyForm = request.getParameter("studyForm");
//        String[] literGenre = request.getParameterValues("litGenre");
//        String[] musicGenre = request.getParameterValues("musicGenre");
//        
//        StringBuilder currData = new StringBuilder();
//        
//        currData.append("Имя студента: ").append(firstName).append("<br>");
//        currData.append("Фамилия студента: ").append(lastName).append("<br>");
//        currData.append("Факультет: ").append(major != null ? major : "не указан").append("<br>");
//        currData.append("Форма обучения: ").append(studyForm != null ? studyForm : "не указана").append("<br>");
//        currData.append("Любимый жанр литературы: ");
//        currData.append(literGenre != null ? 
//            String.join(", ", literGenre):
//            "не указан").append("<br>");
//        currData.append("Любимый жанр музыки: ");
//        currData.append(musicGenre != null ?
//            String.join(", ", musicGenre):
//            "не указан").append("<br>");
//        
//        String prevData = request.getParameter("history");
//        if (prevData == null) prevData = "";
//        prevData += currData.toString();
////        request.setAttribute("history", prevData);
////        doGet(request, response);
//        
//        response.sendRedirect("?" + "history=" + java.net.URLEncoder.encode(prevData, "UTF-8"));
//        doGet(request, response);

    //        if(firstName != null || lastName != null){
//            out.println("Имя студента: " + firstName + "<br>");
//            out.println("Фамилия студента " + lastName + "<br>");
//        }
//        
//        String litInformation = "Любимый жанр литературы: ";
//        if(literGenre.length == 0)
//            out.println(litInformation + "отсутствует");
//        else{ 
//            out.println(litInformation);
//            foreach(String str in litGenre){
//                out.println(str + " ");
//            }
//        }
//        String musicInformation = "Любимый жанр музыки: ";
//        if(musicInformation.length == 0)
//            out.println(musicInformation + "отсутствует");
//        else{ 
//            out.println(musicInformation);
//            foreach(String str in musicGenre){
//                out.println(str + " ");
//            }
//        }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet FormData</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet FormData at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

//}
