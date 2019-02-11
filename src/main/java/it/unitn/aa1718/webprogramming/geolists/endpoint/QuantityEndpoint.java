package it.unitn.aa1718.webprogramming.geolists.endpoint;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import javafx.util.Pair;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/quantity/{userCookie}")
public class QuantityEndpoint {

    private Session session;
    private static HashMap<Session, Long> userIdFromSession = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userCookie") String userCookie) throws IOException {
        Optional<User> u = (new UserDAO()).getUser(userCookie);
        
        if (!u.isPresent()) {
            UserAnonimousDAO dao = new UserAnonimousDAO();
            Optional<UserAnonimous> ua = dao.getFromCookie(userCookie);
            
            if(ua.isPresent()){
                
                this.onClose(session);
                session.getBasicRemote().sendText(new MessageJson("server", "you are anonymous", "").toJson());
                
            }
            else
                session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
            
        } else {
            session.getBasicRemote().sendText(new MessageJson("server", "connection succesfull", "").toJson());
            userIdFromSession.put(session, u.get().getId());
        }
    }

    @OnMessage
    public void onMessage(Session session, String txt)
            throws IOException {
        long userId = userIdFromSession.get(session);
        
        MessageSession message = new MessageSession(txt);
        long itemId=message.getItemId();
        long listId=message.getListId();
        AccessDAO accessDAO = new AccessDAO();
        
        ItemDAO itemDAO = new ItemDAO();
        Optional<Item> itemOpt = itemDAO.get(itemId);
        
        ProductListDAO listDAO = new ProductListDAO();
        Optional<ProductList> listOpt = listDAO.get(listId);
        
        ComposeDAO composeDAO = new ComposeDAO();
        Optional<Compose> composeOpt = composeDAO.getComposeObjectFromItemIdListId(itemId, listId);
        
        if (!itemOpt.isPresent() || !listOpt.isPresent() || !composeOpt.isPresent()) {
            session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
        }
        if(!accessDAO.canHaveAccess(userId, listId)){
            session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
        } else {
            
            int quantity = composeOpt.get().getQuantity();
            boolean take = composeOpt.get().isTake();
            
            //gestire le operazioni
            switch (message.getOp()) {
                case "+":
                    quantity++;
                    break; 
                case "-":
                    if (quantity > 1)
                        quantity--;
                    break;
                case "k":
                    take=!take;
                    break;
                default:
                    session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
                    break;
            }
            
            Compose c = new Compose(listId, itemId, quantity,take);
            composeDAO.updateQuantity(c);
        }

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("chiudo sessione");
        userIdFromSession.remove(session);
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
    
    private boolean possiedeAnonimamente(long aninimoid,long listid){
        ProductListDAO pDAO = new ProductListDAO();
        
        ProductList plist = pDAO.getListAnon(aninimoid).get();
        
        return (plist.getId()==listid);
    }

}




