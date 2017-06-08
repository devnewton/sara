package im.bci.sara;

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
public class AvenirEnCommun implements SaraAction {

    private static final Pattern PHI = Pattern.compile("\\b(jlm|jean\\sluc|((m[ée]l([ea]n|u))|holo)ch?(on|e)|avenir\\sen\\scommun|phi|france\\sinsoumise?|φ)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public MatchLevel match(Post post) {
        return PHI.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.NO;
    }

    @Override
    public String reply(Post post) {
        try {
            Document doc = Jsoup.connect("https://laec.fr/hasard").get();
            String sujet = doc.select("#contenu .subject-foreword").text();
            if (StringUtils.isNotBlank(sujet)) {
                return sujet;
            }
        } catch (Exception ex) {
            LogFactory.getLog(this.getClass()).error("avenir en commun error", ex);
        }
        return null;
    }

}
