/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Models;

import java.time.LocalDate;

/**
 *
 * @author vanni
 */
public interface IBoardgame {
    public int getId();
    
    public void setId(int newId);
    
    public String getName();
    
    public void setName(String newName);
    
    public LocalDate getReleaseDate();
    
    public void setReleaseDate(LocalDate newReleaseDate);
    
    public String getDesigner();
    
    public void setDesigner(String newDesigner);
    
    public float getPrice();
    
    public void setPrice(float newPrice);
}
