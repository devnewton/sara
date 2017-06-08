package im.bci.sara;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author devnewton
 */
@RestController
@RequestMapping("/")
public class SaraController {

    @Autowired
    private Sara sara;

    @ResponseBody
    @RequestMapping(path = "")
    public String onPost(Post post) {
        if(null != post && StringUtils.isNotBlank(post.getMessage())) {
            return sara.reply(post);
        }
        return "";
    }

}
