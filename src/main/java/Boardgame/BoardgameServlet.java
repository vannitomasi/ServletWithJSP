/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame;

import Boardgame.Data.Models.Boardgame;
import Boardgame.Data.Utils.BoardgameQuery;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TO DO 2021/05/04 TomasiV compiler says "uses unchecked or unsafe operations."
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
            throws IOException, ServletException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
                
        // TO DO 2021/4/1 TomasiV create a BoardgameClass
        // TO DO 2021/4/1 TomasiV create a constant class
        Boardgame boardgame = new Boardgame(
            request.getParameter("boardgameId"),
            request.getParameter("boardgameName"),
            request.getParameter("boardgameReleaseDate"),
            request.getParameter("boardgameDesigner"),
            request.getParameter("boardgamePrice")
        );
        try {
            List<Boardgame> boardgames = BoardgameQuery.getByFilter(boardgame);
            
            request.setAttribute("boardgameList", boardgames);
            this.getServletContext().getRequestDispatcher("/boardgame-home.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BoardgameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BoardgameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
