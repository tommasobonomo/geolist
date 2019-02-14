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

@ServerEndpoint(value = "/anonymous/{userCookie}")
public class AnonymousEndpoint {

    private Session session;
    private static HashMap<Session, Long> userIdFromSession = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userCookie") String userCookie) throws IOException {
        Optional<UserAnonimous> userAnonymousOpt = (new UserAnonimousDAO()).getFromCookie(userCookie);

        if (!userAnonymousOpt.isPresent()) {

            session.getBasicRemote().sendText(new MessageJson("server", "bad request, connection failed", "").toJson());

        } else {
            session.getBasicRemote().sendText(new MessageJson("server", "connection succesfull", "").toJson());
            userIdFromSession.put(session, userAnonymousOpt.get().getId());
        }
    }

    @OnMessage
    public void onMessage(Session session, String txt)
            throws IOException {
        long userAnonymousId = userIdFromSession.get(session);

        MessageSession message = new MessageSession(txt, 1);
        long itemId = message.getId1();

        ItemDAO itemDAO = new ItemDAO();
        Optional<Item> itemOpt = itemDAO.get(itemId);

        ProductListDAO listDAO = new ProductListDAO();
        Optional<ProductList> listAnonymousOpt = listDAO.getListAnon(userAnonymousId);

        ComposeDAO composeDAO = new ComposeDAO();

        if (!itemOpt.isPresent() || !listAnonymousOpt.isPresent()) {
            session.getBasicRemote().sendText(new MessageJson("server", "bad request, item or list don't exist", "").toJson());
        } else {
            long listId = listAnonymousOpt.get().getId();
            Optional<Compose> composeOpt = composeDAO.getComposeObjectFromItemIdListId(itemId, listId);

            if (composeOpt.isPresent()) {
                int quantity = composeOpt.get().getQuantity();
                boolean take = composeOpt.get().isTake();

                //gestire le operazioni
                switch (message.getOp()) {
                    case "+":
                        quantity++;
                        break;
                    case "-":
                        if (quantity > 1) {
                            quantity--;
                        }
                        break;
                    case "k":
                        take = !take;
                        break;
                    default:
                        session.getBasicRemote().sendText(new MessageJson("server", "bad request", "").toJson());
                        break;
                }

                Compose c = new Compose(listId, itemId, quantity, take);
                composeDAO.updateQuantity(c);
            } else {
                session.getBasicRemote().sendText(new MessageJson("server", "item is not in the list", "").toJson());
            }
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

    

}
