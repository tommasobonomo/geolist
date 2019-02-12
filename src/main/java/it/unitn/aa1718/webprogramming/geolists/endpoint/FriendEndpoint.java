package it.unitn.aa1718.webprogramming.geolists.endpoint;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.IsFriendDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Access;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/friend/{userCookie}/{listid}")
public class FriendEndpoint {

    private Session session;
    private static HashMap<Session, Long> userIdFromSession = new HashMap<>();
    private static HashMap<Session, Long> listIdFromSession = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userCookie") String userCookie, @PathParam("listid") String listid) throws IOException {
        
        if (Long.valueOf(listid) == null) {
            this.onClose(session);
            session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
        } else {
            long listId = Long.valueOf(listid);
            
            Optional<ProductList> pl = (new ProductListDAO()).get(listId);
            Optional<User> u = (new UserDAO()).getUser(userCookie);

            if (!u.isPresent() || !pl.isPresent()) {
                UserAnonimousDAO dao = new UserAnonimousDAO();
                Optional<UserAnonimous> ua = dao.getFromCookie(userCookie);

                this.onClose(session);
                session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());

            } else {
                AccessDAO accessDAO = new AccessDAO();
                long userId = u.get().getId();
                
                if(accessDAO.havePermission(userId, listId)){
                    userIdFromSession.put(session, userId);
                    listIdFromSession.put(session, listId);
                    session.getBasicRemote().sendText(new MessageJson("server", "connection succesfull", "").toJson());
                } else {
                    session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
                    this.onClose(session);
                }
                
            }
        }
    }

    @OnMessage
    public void onMessage(Session session, String txt)
            throws IOException {
        long userId = userIdFromSession.get(session);
        long listId = userIdFromSession.get(session);
        
        MessageSession message = new MessageSession(txt,1); //op, friendId
        long friendId=message.getId1();
        
        IsFriendDAO isFriendDAO = new IsFriendDAO();
        AccessDAO accessDAO = new AccessDAO();
        UserDAO userDAO = new UserDAO();
        
        if(!userDAO.get(friendId).isPresent()){
            session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
            this.onClose(session);
        } else if(isFriendDAO.isFriend(userId, friendId) && accessDAO.canHaveAccess(friendId, listId)){
            boolean havePermission = accessDAO.havePermission(friendId, listId);
            Access access = new Access(friendId,listId,!havePermission);
            
            //gestire le operazioni
            switch (message.getOp()) {
                case "perm":
                    accessDAO.updatePermission(access);
                    break; 
                default:
                    session.getBasicRemote().sendText(new MessageJson("server", "+perm or -perm bad request", "").toJson());
                    break;
            }
            
        } else {
            session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
            this.onClose(session);
        }
        

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("chiudo sessione");
        userIdFromSession.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Errore aggiunta permessi live");
        throwable.printStackTrace(System.out);
    }

}




