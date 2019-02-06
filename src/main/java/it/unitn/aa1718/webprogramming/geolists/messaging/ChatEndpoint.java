package it.unitn.aa1718.webprogramming.geolists.messaging;

import it.unitn.aa1718.webprogramming.geolists.database.AccessDAO;
import it.unitn.aa1718.webprogramming.geolists.database.MessageDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
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

@ServerEndpoint(value = "/chat/{listid}/{userCookie}")
public class ChatEndpoint {

    private Session session;
    private static HashMap<Integer, Long> chatFrom = new HashMap<>(); //utenti attivi della chat
    private static HashMap<String, Long> usersFrom = new HashMap<>(); // dalla sessione ricavo l'utente
    private static HashMap<Long, Session> sessionsFrom = new HashMap<>(); // dall'utente ricavo la sessione

    @OnOpen
    public void onOpen(Session session, @PathParam("userCookie") long userCookie, @PathParam("listid") long listid) throws IOException {
        Optional<User> u = (new UserDAO()).getUser(String.valueOf(userCookie));

        if (!u.isPresent()) {
            session.getBasicRemote().sendText(new MessageJson("error", "bad request", "").toJson());
        } else {
            long userid = u.get().getId();
            //setto qual'è la sua sessione
            this.session = session;
            usersFrom.put(session.getId(), userid);
            sessionsFrom.put(userid, session);
            chatFrom.put(hashcode(userid, session.getId()), listid);

            //setto qual'è la sua chat
            System.out.println("connected");

            UserDAO userDAO = new UserDAO();
            MessageDAO msgDao = new MessageDAO();
            List<Message> messages = msgDao.getMessageFromList(listid);
            List<MessageJson> messagesJson = new ArrayList<>();

            for (Message m : messages) {
                String username = userDAO.get(m.getIdUser()).get().getUsername();
                String txt = m.getText();
                String sendTime = m.getSendTime().toString();

                session.getBasicRemote().sendText(new MessageJson(username, txt, sendTime).toJson());
            }

        }
    }

    @OnMessage
    public void onMessage(Session session, String txt)
            throws IOException {
        long userId = usersFrom.get(session.getId());
        UserDAO userDAO = new UserDAO();
        String username = userDAO.get(userId).get().getUsername();
        long listId = chatFrom.get(hashcode(userId, session.getId()));

        Timestamp sendTime = new Timestamp((new Date()).getTime());

        //aggiornodb
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.create(new Message(userId, listId, sendTime, txt));

        AccessDAO accessDAO = new AccessDAO();
        List<User> usersInList = accessDAO.getUser(listId);

        MessageJson m = new MessageJson(username, txt);
        m.setCurrentTime();

        for (User u : usersInList) {
            if (sessionsFrom.containsKey(u.getId())
                    && //controllo utente effettivamente attivo
                    chatFrom.get(hashcode(u.getId(), sessionsFrom.get(u.getId()).getId())) == listId) //nella sessione della chat giusta
            {
                sessionsFrom.get(u.getId()).getBasicRemote().sendText(m.toJson());
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        long userId = usersFrom.get(session.getId());
        System.out.println("chiudo sessione");

        chatFrom.remove(hashcode(userId, session.getId()));
        usersFrom.remove(session.getId());
        sessionsFrom.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Errore nella messaggistica live");
        throwable.printStackTrace(System.out);
    }

    private int hashcode(long a, String b) {
        return (new Pair(a, b)).hashCode();
    }

}

class MessageJson {

    String authorName;
    String sendTime = "no set";
    String text;

    public MessageJson(String authorName, String text, String sendTime) {
        this.authorName = authorName;
        this.text = text;
        this.sendTime = sendTime;
    }

    public MessageJson(String authorName, String text) {
        this.authorName = authorName;
        this.text = text;
    }

    public void setCurrentTime() {
        this.sendTime = new Timestamp((new Date()).getTime()).toString();
    }

    public String toJson() {
        return "{\"authorName\": \"" + authorName + "\", \"text\": \"" + text + "\", \"sendTime\": \"" + sendTime + "\"}";
    }

}
