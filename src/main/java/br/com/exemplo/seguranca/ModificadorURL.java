package br.com.exemplo.seguranca;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.config.rewrite.RewriteRule;
import com.ocpsoft.pretty.faces.rewrite.Processor;
import com.ocpsoft.pretty.faces.url.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ModificadorURL implements Processor {

    @Override
    public String processInbound(HttpServletRequest hsr, HttpServletResponse hsr1, RewriteRule rr, String string) {
        PrettyContext context = PrettyContext.getCurrentInstance(hsr);
        URL u = context.getRequestURL();

        for (UrlMapping map : context.getConfig().getMappings()) {
            if (u.toURL().equals("/faces/login.xhtml")) {
                break;
            }
            if (map.getViewId().equals(u.toURL())) {
                string = map.getPattern();
            }
        }

        if (!context.getConfig().isURLMapped(u)) {
            URL u2 = new URL(u + "/");
            if (context.getConfig().isURLMapped(u2)) {
                string += "/";
            }
        }
        return string;
    }

    @Override
    public String processOutbound(HttpServletRequest hsr, HttpServletResponse hsr1, RewriteRule rr, String string) {
        return string;
    }


}
