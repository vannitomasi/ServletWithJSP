/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame;

import Boardgame.Data.Models.Boardgame;
import Boardgame.Data.Models.IBoardgame;
import Boardgame.Data.Utils.BoardgameQuery;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vanni
 */
@WebServlet(name = "BoardgameCreateServlet", urlPatterns = {"/boardgameCreate"})
public class BoardgameCreateServlet extends HttpServlet {

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
        String boardgameToEditIdString = request.getParameter("id");
        
        String errorMessage = null;
        IBoardgame boardgame = null;
        if (boardgameToEditIdString != null && !boardgameToEditIdString.isEmpty()) {
            try {
                boardgame = BoardgameQuery.getById(Integer.parseInt(boardgameToEditIdString));
            } catch (SQLException ex) {
                errorMessage = ex.getMessage();
                Logger.getLogger(BoardgameEditServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        request.setAttribute("boardgame", boardgame);
        request.setAttribute("errorMessage", errorMessage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/main/boardgame-create.jsp").forward(request, response);
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
        IBoardgame boardgame = new Boardgame(
            request.getParameter("boardgameId"),
            request.getParameter("boardgameName"),
            request.getParameter("boardgameReleaseDate"),
            request.getParameter("boardgameDesigner"),
            request.getParameter("boardgamePrice")
        );
        
        String errorMessage = null;
        try {
            if(!BoardgameQuery.create(boardgame)) {
                errorMessage = "Error while creating boardgame";
            }
        } catch (SQLException ex) {            
            errorMessage = ex.getMessage();
            Logger.getLogger(BoardgameEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("errorMessage", errorMessage);        
        if (errorMessage == null || errorMessage.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/boardgameList");
        } else {
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/main/boardgame-edit.jsp")
                    .forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "GET: get specific boardgame info; POST: add specific boardgame values on database";
    }// </editor-fold>

}
