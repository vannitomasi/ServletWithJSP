/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Business.Servlet;

import Boardgame.Business.Utils.BusinessUtils;
import Boardgame.Data.Models.Boardgame;
import Boardgame.Data.Models.IBoardgame;
import Boardgame.Data.Utils.BoardgameQuery;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "BoardgameListServlet", urlPatterns = {BusinessUtils.BOARDGAME_LIST_URL_ROOT})
public class BoardgameListServlet extends HttpServlet {    
    
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
            throws IOException, ServletException {        
        // TO DO 2021/4/1 TomasiV create a constant class
        Boardgame boardgame = new Boardgame(
            request.getParameter(BusinessUtils.BOARDGAME_ID_PARAMETER_NAME),
            request.getParameter(BusinessUtils.BOARDGAME_NAME_PARAMETER_NAME),
            request.getParameter(BusinessUtils.BOARDGAME_RELEASE_DATE_PARAMETER_NAME),
            request.getParameter(BusinessUtils.BOARDGAME_DESIGNER_PARAMETER_NAME),
            request.getParameter(BusinessUtils.BOARDGAME_PRICE_PARAMETER_NAME)
        );
        String errorMessage = null;
        ArrayList<IBoardgame> boardgames = null;
        try {
            boardgames = BoardgameQuery.getByFilter(boardgame);
        } catch (SQLException ex) {
            errorMessage = ex.getMessage();
            Logger.getLogger(BoardgameListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute(BusinessUtils.BOARDGAME_LIST_PARAMETER_NAME, boardgames);
        request.setAttribute(BusinessUtils.ERROR_MESSAGE_PARAMETER_NAME, errorMessage);
        this.getServletContext().getRequestDispatcher(BusinessUtils.BOARDGAME_HOME_PAGE_PATH).forward(request, response);
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
        return "Get the list of boardgames on database and forward them to the jsp view";
    }

}
