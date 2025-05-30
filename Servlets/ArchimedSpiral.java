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
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Base64;

@WebServlet(urlPatterns = { "/ArchimedSpiral" })
public class ArchimedSpiral extends HttpServlet {
    private int width = 800, height = 800;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Спираль Архимеда</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form method='post'>");
        out.println("<label for='step'>Количество витков</label>");
        out.println("<input type='text' id='step' name='step' required><br>");
        out.println("<label for='alpha'>Alpha:</label>");
        out.println("<input type='text' id='alpha' name='alpha' required>");
        out.println("<input type='submit' value='Построить спираль'>");
        out.println("</form>");

        int cX = width / 2;
        int cY = height / 2;
        double prevX1 = cX, prevY1 = cY;
        double prevX2 = cX, prevY2 = cY;

        String stepParam = request.getParameter("step");
        String alphaParam = request.getParameter("alpha");

        if (stepParam != null && alphaParam != null) {

            try {
                double step = Double.parseDouble(stepParam);
                double alpha = Double.parseDouble(alphaParam);
                double phi = 1.0;

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2f));

                while (phi < step * Math.PI * 2) {
                    double rho = alpha * (phi);

                    double x1 = cX + rho * Math.cos(phi);
                    double y1 = cY + rho * Math.sin(phi);

                    double x2 = cX - rho * Math.cos(phi);
                    double y2 = cY - rho * Math.sin(phi);

                    g2d.drawLine((int) prevX1, (int) prevY1, (int) x1, (int) y1);
                    g2d.drawLine((int) prevX2, (int) prevY2, (int) x2, (int) y2);

                    prevX1 = x1;
                    prevY1 = y1;
                    prevX2 = x2;
                    prevY2 = y2;

                    phi += 0.1;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                out.println("<img src='data:image/png;base64," + base64Image + "' alt='Спираль Архимеда'>");
            } catch (NumberFormatException e) {
                out.println("<p>Ошибка: Некорректные параметры</p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
