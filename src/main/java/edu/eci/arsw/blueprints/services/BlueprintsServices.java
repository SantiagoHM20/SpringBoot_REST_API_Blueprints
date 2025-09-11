/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    private final BlueprintsPersistence bpp;
   
    @Autowired
    public BlueprintsServices(BlueprintsPersistence bpp){
        this.bpp = bpp;
    }


    
    public void addNewBlueprint(Blueprint bp){
        try{
            bpp.saveBlueprint(bp);
        }
        catch (BlueprintPersistenceException e){
            System.out.println("Unable to save that Blueprint"+ e.getMessage());
        }
        
    }
    
    public Set<Blueprint> getAllBlueprints(){
        try {
            return bpp.getAllBlueprints();
        }
        catch (BlueprintPersistenceException e){
            System.out.println("Any Blueprints found "+ e.getMessage());
            return null;
        }

    }

    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) {
        try {
            return bpp.getBlueprint(author, name);
        } catch (BlueprintNotFoundException e) {
            System.out.println("Blueprint not found: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        try{
        return bpp.getBlueprintsByAuthor(author);
        }
        catch(BlueprintNotFoundException e){
            System.out.println("Blueprint not found: " + e.getMessage());
            return null;
        }
    }
    
}
