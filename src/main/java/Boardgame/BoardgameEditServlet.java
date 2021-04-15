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
@WebServlet(name = "BoardgameEditServlet", urlPatterns = {"/boardgameEdit"})
public class BoardgameEditServlet extends HttpServlet {

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
        this.getServletContext().getRequestDispatcher("/WEB-INF/main/boardgame-edit.jsp").forward(request, response);
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
        boolean isUpdateSuccessful = false;
        try {
            isUpdateSuccessful = BoardgameQuery.update(boardgame);
            // TO DO 2021/04/11 TomasiV on error it resets values
            // throw new SQLException("What has happened?");
        } catch (SQLException ex) {            
            errorMessage = ex.getMessage();
            Logger.getLogger(BoardgameEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("errorMessage", errorMessage);
        if (isUpdateSuccessful) {
            request.setAttribute("successMessage", "Boardgame '" + boardgame.getName() + "' successfully updated");
        }
        
        if (errorMessage == null || errorMessage.isEmpty()) {
            request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/main/boardgame-home.jsp")
                    .forward(request, response);
            // TO DO 2021/04/11 TomasiV redirecting to the servlet "loses" request attributes, fix
            // response.sendRedirect(request.getContextPath() + "/boardgameList");
        } else {
            request.setAttribute("boardgameId", boardgame.getId());
            request.setAttribute("boardgameName", boardgame.getName());
            request.setAttribute("boardgameReleaseDate", boardgame.getReleaseDate().toString());
            request.setAttribute("boardgameDesigner", boardgame.getDesigner());
            request.setAttribute("boardgamePrice", ((Float)boardgame.getPrice()).toString());
        
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
        return "GET: get specific boardgame info; POST: change specific boardgame values on database";
    }// </editor-fold>

}