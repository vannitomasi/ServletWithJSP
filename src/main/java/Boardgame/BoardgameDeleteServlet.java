/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame;

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
@WebServlet(name = "BoardgameDeleteServlet", urlPatterns = {"/boardgameDelete"})
public class BoardgameDeleteServlet extends HttpServlet {

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
        String errorMessage = null;
        String boardgameIdToDeleteString = request.getParameter("id");
        if (boardgameIdToDeleteString != null && !boardgameIdToDeleteString.isEmpty()) {
            try {
                int boardgameIdToDelete = Integer.parseInt(boardgameIdToDeleteString);
                if (BoardgameQuery.delete(boardgameIdToDelete)) {
                    request.setAttribute("errorMessage", "Error while deleting boardgame");
                }
                throw new SQLException("Prova errore delete");
            } catch (SQLException ex) {
                errorMessage = ex.getMessage();
                Logger.getLogger(BoardgameEditServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("errorMessage", errorMessage);
        }
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/main/boardgame-home.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "POST: delete specific boardgame from database";
    }// </editor-fold>

}
