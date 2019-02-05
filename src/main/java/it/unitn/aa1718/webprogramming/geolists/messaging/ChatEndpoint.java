
package it.unitn.aa1718.webprogramming.geolists.messaging;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.MessageDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Message;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/chat/{listid}/{userid}")
public class ChatEndpoint {
    
    private Session session;
    private static HashMap<Integer, Long> chatFrom = new HashMap<>(); //utenti attivi della chat
    private static HashMap<String, Long> usersFrom = new HashMap<>(); // dalla sessione ricavo l'utente
    private static HashMap<Long, Session> sessionsFrom = new HashMap<>(); // dall'utente ricavo la sessione
 
    @OnOpen
    public void onOpen(Session session, @PathParam("userid") long userid, @PathParam("listid") long listid) throws IOException {
        

        //setto qual'è la sua sessione
        this.session = session;
        usersFrom.put(session.getId(),userid);
        sessionsFrom.put(userid,session);
        chatFrom.put(hashcode(userid,session.getId()), listid);
        
        //setto qual'è la sua chat
        System.out.println(userid+" "+listid+ " " +session.getId());
        session.getBasicRemote().sendText("connesso");
    }
    
    @OnMessage
    public void onMessage(Session session, String txt) 
      throws IOException {
        long userId = usersFrom.get(session.getId());
        System.out.println(userId);
        long listId = chatFrom.get(hashcode(userId,session.getId()));
        
        Timestamp sendTime = new Timestamp((new Date()).getTime());
        
        
        //aggiornodb
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.create(new Message(userId, listId, sendTime, txt));
        
        AccessDAO accessDAO = new AccessDAO();
        List <User> usersInList = accessDAO.getUser(listId);
        
        for(User u : usersInList ){
            if(sessionsFrom.containsKey(u.getId()) && //controllo utente effettivamente attivo
                    chatFrom.get(hashcode(u.getId(),sessionsFrom.get(u.getId()).getId()))==listId) //nella sessione della chat giusta
                sessionsFrom.get(u.getId()).getBasicRemote().sendText("newMessage");
        }
    }
      
    @OnClose
    public void onClose(Session session) throws IOException {
        long userId = usersFrom.get(session.getId());
        System.out.println("chiudo sessione");
        
        chatFrom.remove(hashcode(userId,session.getId()));
        usersFrom.remove(session.getId());
        sessionsFrom.remove(userId);
        
    }
   
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Errore nella messaggistica live");
        throwable.printStackTrace(System.out);
    }
    
    private int hashcode(long a, String b){
        return (new Pair(a,b)).hashCode();
    }
}