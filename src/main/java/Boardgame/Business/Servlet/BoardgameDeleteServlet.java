/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Business.Servlet;

import Boardgame.Business.Utils.BusinessUtils;
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
@WebServlet(name = "BoardgameDeleteServlet", urlPatterns = {BusinessUtils.BOARDGAME_DELETE_URL_ROOT})
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
        String boardgameIdToDeleteString = request.getParameter(BusinessUtils.ID_PARAMETER_NAME);
        if (boardgameIdToDeleteString != null && !boardgameIdToDeleteString.isEmpty()) {
            try {
                int boardgameIdToDelete = Integer.parseInt(boardgameIdToDeleteString);
                if (!BoardgameQuery.delete(boardgameIdToDelete)) {
                    errorMessage = BusinessUtils.getBoardgameErrorWithPhaseString("deleting");
                }
            } catch (SQLException ex) {
                errorMessage = ex.getMessage();
                Logger.getLogger(BoardgameDeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute(BusinessUtils.ERROR_MESSAGE_PARAMETER_NAME, errorMessage);
        }
        
        response.sendRedirect(request.getContextPath() + BusinessUtils.BOARDGAME_LIST_URL_ROOT);
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
