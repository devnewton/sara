package im.bci.sara;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author devnewton
 */
@Component
public class JourneeMondiale implements SaraAction {

    private static final Pattern JOURNEE = Pattern.compile("\\b(journées?|jours?|aujourd)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public MatchLevel match(Post post) {
        return JOURNEE.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.CAN;
    }

    @Override
    public String reply(Post post) {
        try {
            Document doc = Jsoup.connect("http://www.journee-mondiale.com/").get();
            String journee = doc.select("#journeesDuJour > article:nth-child(1) > a:nth-child(1) > h2:nth-child(2)").text();
            if (StringUtils.isNotBlank(journee)) {
                return "Aujourd'hui, c'est la " + journee + ". Hihi!";
            }
        } catch (IOException ex) {
            LogFactory.getLog(this.getClass()).error("journee mondiale error", ex);
        }
        return null;
    }

}
