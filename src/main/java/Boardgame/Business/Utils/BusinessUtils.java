/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Business.Utils;

/**
 *
 * @author vanni
 */
public class BusinessUtils {
    
    public static final String BOARDGAME_DESIGNER_PARAMETER_NAME = "boardgameDesigner";
    public static final String BOARDGAME_ID_PARAMETER_NAME = "boardgameId";
    public static final String BOARDGAME_LIST_PARAMETER_NAME = "boardgameList";
    public static final String BOARDGAME_NAME_PARAMETER_NAME = "boardgameName";
    public static final String BOARDGAME_OBJECT_PARAMETER_NAME = "boardgame";
    public static final String BOARDGAME_PRICE_PARAMETER_NAME = "boardgamePrice";
    public static final String BOARDGAME_RELEASE_DATE_PARAMETER_NAME = "boardgameReleaseDate";
    
    public static final String BOARDGAME_CREATE_PAGE_PATH = "/WEB-INF/main/boardgame-create.jsp";
    public static final String BOARDGAME_HOME_PAGE_PATH = "/WEB-INF/main/boardgame-home.jsp";
    public static final String BOARDGAME_UPDATE_PAGE_PATH = "/WEB-INF/main/boardgame-update.jsp";
        
    public static final String BOARDGAME_CREATE_URL_ROOT = "/boardgameCreate";
    public static final String BOARDGAME_DELETE_URL_ROOT = "/boardgameDelete";
    public static final String BOARDGAME_LIST_URL_ROOT = "/boardgameList";
    public static final String BOARDGAME_UPDATE_URL_ROOT = "/boardgameUpdate";
    
    public static final String ERROR_MESSAGE_PARAMETER_NAME = "errorMessage";
    public static final String ID_PARAMETER_NAME = "id";
    
    public static final String getBoardgameErrorWithPhaseString(String phase) {
        return BusinessUtils.getErrorWithPhaseString(phase, "boardgame");
    }
    
    public static final String getErrorWithPhaseString(String phase, String entity) {
        StringBuilder errorString = new StringBuilder("Error while ");
        errorString
                .append(phase)
                .append(" ")
                .append(entity);
        return errorString.toString();
    }
}
