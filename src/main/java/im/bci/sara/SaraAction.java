package im.bci.sara;

import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author devnewton
 */
public interface SaraAction {
    
    public enum MatchLevel {
        NO,
        CAN,
        MUST        
    }
    MatchLevel match(Post post);
    String reply(Post post);
}
