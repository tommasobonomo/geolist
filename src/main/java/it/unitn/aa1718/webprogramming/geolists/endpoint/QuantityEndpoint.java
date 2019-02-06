package it.unitn.aa1718.webprogramming.geolists.endpoint;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.MessageDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.Message;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.util.Pair;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/quantity/{listid}/{userCookie}")
public class QuantityEndpoint {

    private Session session;
    private static HashMap<Session, Long> listIdFromSession = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userCookie") long userCookie, @PathParam("listid") long listid) throws IOException {
        Optional<User> u = (new UserDAO()).getUser(String.valueOf(userCookie));

        if (!u.isPresent()) {
            session.getBasicRemote().sendText(new MessageJson("error", "bad request", "").toJson());
        } else {
            session.getBasicRemote().sendText(new MessageJson("success", "connection succesfull", "").toJson());
            listIdFromSession.put(session, listid);

        }
    }

    @OnMessage
    public void onMessage(Session session, String txt)
            throws IOException {
        long listId = listIdFromSession.get(session);
        
        Pair <String,Long> parsedString = parse(txt);
        long itemId = parsedString.getValue();
        String operation = parsedString.getKey();

        ItemDAO itemDAO = new ItemDAO();
        Optional<Item> itemOpt = itemDAO.get(itemId);

        if (!itemOpt.isPresent()) {
            session.getBasicRemote().sendText(new MessageJson("error", "bad request", "").toJson());
        } else {
            ComposeDAO composeDAO = new ComposeDAO();
            int quantity = composeDAO.getQauntityFromItemAndList(itemId, listId).get();
            
            System.out.println("operator :"+operation);
            if(operation.equals("+"))
                quantity++;
            else if(operation.equals("-")){
                if (quantity > 1) 
                        quantity--;
            }        
            else
                 session.getBasicRemote().sendText(new MessageJson("error", "bad request", "").toJson());
            
            
            

            Compose c = new Compose(listId, itemId, quantity);
            composeDAO.updateQuantity(c);
            session.getBasicRemote().sendText(new MessageJson(String.valueOf(itemId), operation, "").toJson());

        }

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("chiudo sessione");
        listIdFromSession.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Errore nella messaggistica live");
        throwable.printStackTrace(System.out);
    }

    private int hashcode(long a, String b) {
        return (new Pair(a, b)).hashCode();
    }

    private Pair<String,Long> parse(String s){
        
        String[] words = s.split(" ");
        System.out.println(s);
        
        return new Pair(words[0],Long.valueOf(words[1]));
    }

}
