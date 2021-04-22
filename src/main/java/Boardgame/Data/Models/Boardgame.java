/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boardgame.Data.Models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author vanni
 */
public class Boardgame implements IBoardgame, Serializable {
    private int id;
    private String name;
    private LocalDate releaseDate;
    private String designer;
    private float price;
    
    public Boardgame() { }
    
    public Boardgame(Integer newId, String newName, LocalDate newReleaseDate, String newDesigner, float newPrice) {
        this.designer = newDesigner;
        this.id = newId;
        this.name = newName;
        this.price = newPrice;
        this.releaseDate = newReleaseDate;
    }
    
    public Boardgame(String newId, String newName, String newReleaseDate, String newDesigner, String newPrice) {
        if (newDesigner != null && !newDesigner.isEmpty()) {
            this.designer = newDesigner;
        }
        
        if (newId != null && !newId.isEmpty()) {
            this.id = Integer.parseInt(newId);
        }
        
        this.name = newName;
        
        if (newPrice != null && !newPrice.isEmpty()) {
            this.price = Float.parseFloat(newPrice);
        }
        
        if (newReleaseDate != null && !newReleaseDate.isEmpty()) {
            this.releaseDate = LocalDate.parse(newReleaseDate);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int newId) {
        this.id = newId;
    }
    
    public String getName() {
        return this.name == null ? "" : this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public LocalDate getReleaseDate() {
        return this.releaseDate == null ? LocalDate.MIN : this.releaseDate;
    }
    
    public void setReleaseDate(LocalDate newReleaseDate) {
        this.releaseDate = newReleaseDate;
    }
    
    public String getDesigner() {
        return this.designer == null ? "" : this.designer;
    }
    
    public void setDesigner(String newDesigner) {
        this.designer = newDesigner;
    }
    
    public float getPrice() {
        return this.price;
    }
    
    public void setPrice(float newPrice) {
        this.price = newPrice;
    }
}
