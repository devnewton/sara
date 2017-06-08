package im.bci.sara;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class Moinsbete implements SaraAction {

    private static final Pattern JOURNEE = Pattern.compile("\\b(moins\\s*b[eê]tes?)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public MatchLevel match(Post post) {
        return JOURNEE.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.CAN;
    }

    @Override
    public String reply(Post post) {
        try {
            Document doc = Jsoup.connect("http://secouchermoinsbete.fr/random").get();
            Element anecdote = doc.select(".anecdote-content-wrapper .summary a").first();
            anecdote.select(".read-more").remove();
            if (StringUtils.isNotBlank(anecdote.text())) {
                return anecdote.text();
            }
        } catch (Exception ex) {
            LogFactory.getLog(this.getClass()).error("moinsbete error", ex);
        }
        return null;
    }

}
