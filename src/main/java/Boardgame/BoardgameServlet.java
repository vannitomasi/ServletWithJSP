/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame;

import Boardgame.Data.SingletonDatabaseContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vanni
 */
public class BoardgameServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            /* Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/boardgame_database");
            Connection conn2 = ds.getConnection();*/
        
            Connection conn = SingletonDatabaseContext.getInstance().getConnection();
            
            Statement statement = conn.createStatement();
            String sql = "select id, name, release_date, designer, price from boardgame_table";
            ResultSet rs = statement.executeQuery(sql);            

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */            
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>BoardgameServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<table border=\"1\" cellpadding=\"5\">");
                out.println("<caption><h2>Boardgame list</h2></caption>");
                out.println("<tr>");
                out.println("    <th>Id</th>");
                out.println("    <th>Name</th>");
                out.println("    <th>Release date</th>");
                out.println("    <th>Designer</th>");
                out.println("    <th>Price</th>");
                out.println("</tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println(String.format("<td>%s</td>", rs.getString("id")));
                    out.println(String.format("<td>%s</td>", rs.getString("name")));
                    out.println(String.format("<td>%s</td>", rs.getString("release_date")));
                    out.println(String.format("<td>%s</td>", rs.getString("designer")));
                    out.println(String.format("<td>%s</td>", rs.getString("price")));
                    out.println("</tr>");
                }            
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
