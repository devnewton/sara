package im.bci.sara;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sara {

    public static final String NAME = "sara";

    @Autowired
    private SaraAction[] actions;

    public String reply(final Post post) {
        try {
            if (isBotCall(post)) {
                List<SaraAction> musts = new ArrayList<>();
                List<SaraAction> cans = new ArrayList<>();
                for (SaraAction action : actions) {
                    switch (action.match(post)) {
                        case MUST:
                            musts.add(action);
                        case CAN:
                            cans.add(action);
                            break;
                        case NO:
                            break;
                    }

                }
                Collections.shuffle(musts);
                for (SaraAction action : musts) {
                    String reply = action.reply(post);
                    if (null != reply) {
                        return reply;
                    }
                }
                Collections.shuffle(cans);
                for (SaraAction action : cans) {
                    String reply = action.reply(post);
                    if (null != reply) {
                        return reply;
                    }
                }
            }
        } catch (Exception ex) {
            LogFactory.getLog(this.getClass()).error(NAME + " bot error", ex);
        }
        return null;
    }

    private boolean isBotCall(Post post) {
        String message = post.getMessage();
        return message.contains("/" + NAME) || message.contains(NAME + "<");
    }

}
