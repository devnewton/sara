package im.bci.sara;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author devnewton
 */
@Component
public class Boobpedia implements SaraAction {

    private static final Pattern BOOB = Pattern.compile("\\b(boob?s?|seins?|nichons?|poitrines?)\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public MatchLevel match(Post post) {
        return BOOB.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.NO;
    }

    @Override
    public String reply(Post post) {
        try {
            Document doc = Jsoup.connect("http://www.boobpedia.com/boobs/Special:Random").timeout(30000).get();
            String boobText = doc.select("#mw-content-text > p:nth-child(2)").text();
            String boobImageSrc = doc.select(".image > img:nth-child(1)").attr("src");
            String boobURI = UriComponentsBuilder.fromUriString("http://www.boobpedia.com").path(boobImageSrc).build().encode().toString();
            if (StringUtils.isNoneBlank(boobText) && StringUtils.isNoneBlank(boobURI)) {
                return boobText + " " + boobURI;
            }
        } catch (IOException ex) {
            LogFactory.getLog(this.getClass()).error("boobpedia error", ex);
        }
        return null;
    }

}
