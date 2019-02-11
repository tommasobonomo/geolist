package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of Access relation
 * @author tommaso
 */
public class Access { 
    private long idUser;
    private long idList;
    private boolean havePermission;
    
    public Access(long idUser,long idList, boolean havePermission){
        this.idList=idList;
        this.idUser=idUser;
        this.havePermission=havePermission;
    }
    
    public void setIdUser(long idUser){
        this.idUser=idUser;
    }
    
    public long getIdUser(){
        return this.idUser;
    }
    
    public void setIdList(long idList){
        this.idList=idList;
    }
    
    public long getIdList(){
        return this.idList;
    }
    
    public void setHavePermission(boolean havePermission){
        this.havePermission=havePermission;
    }
    
    public boolean getHavePermission(){
        return this.havePermission;
    }
    
}
