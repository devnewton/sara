package im.bci.sara;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Meteo implements SaraAction {

    private static final String NAME = "meteo";

    private static final Pattern METEO_PATTERN = Pattern.compile("^.*m[eé]t[eé]o\\s*à?\\s*(?<lieu>.*)$");

    @Value("${sara.meteo.openweathermap.api.key:}")
    private String openWeatherMapApiKey;

    @Override
    public MatchLevel match(Post post) {
        return METEO_PATTERN.matcher(post.getMessage()).find() ? MatchLevel.MUST : MatchLevel.NO;
    }

    @Override
    public String reply(Post post) {
        try {
            Matcher matcher = METEO_PATTERN.matcher(post.getMessage());
            if (matcher.matches()) {
                return postMeteo(matcher.group("lieu"), post);
            }
        } catch (Exception ex) {
            LogFactory.getLog(this.getClass()).error("meteo error", ex);
        }
        return null;
    }

    private String postMeteo(String lieu, Post post) throws IOException {
        if (StringUtils.isBlank(lieu)) {
            lieu = "Nice";
        }
        String meteo = meteoFrance(lieu);
        if (StringUtils.isBlank(meteo)) {
            meteo = openWeatherMap(lieu);
        }
        if (StringUtils.isNotBlank(meteo)) {
            return meteo;
        } else {
            return null;
        }
    }

    private String openWeatherMap(String lieu) throws IOException {
        try {
            if (StringUtils.isNotBlank(openWeatherMapApiKey)) {
                Document doc = Jsoup.connect("http://api.openweathermap.org/data/2.5/weather").data("q", lieu)
                        .data("mode", "html").data("lang", "fr").data("appid", openWeatherMapApiKey).get();
                return Jsoup.clean(doc.html(), Whitelist.none().addTags("a").addAttributes("a", "href"));
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String meteoFrance(String lieu) throws IOException {
        try {
            Document doc = Jsoup.connect("http://www.meteofrance.com/recherche/resultats").data("facet", "previsions")
                    .data("query", lieu).post();
            String meteo = doc.select(".day-data").first().text();
            return meteo;
        } catch (Exception e) {
            return null;
        }
    }

}
