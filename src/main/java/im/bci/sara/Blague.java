package im.bci.sara;

import java.util.regex.Pattern;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

@Component
public class Blague implements SaraAction {

    private static final int MAX_BLAGUE_LINES = 42;

    private static final Pattern BLAGUE = Pattern.compile("\\b(blague?s?|humours?|rires?)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public MatchLevel match(Post post) {
        return BLAGUE.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.NO;
    }

    @Override
    public String reply(Post post) {
        try {
            Document doc = Jsoup.connect("http://www.blague.org/").get();
            doc.select("#humour h1").remove();
            String blague = Jsoup.clean(doc.select("#humour").first().html(), "", Whitelist.none(),
                    new OutputSettings().prettyPrint(false));
            return blague;
        } catch (Exception ex) {
            LogFactory.getLog(this.getClass()).error("blague error", ex);
        }
        return null;
    }

}
