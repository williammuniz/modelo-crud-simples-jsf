package br.com.exemplo.seguranca;

import br.com.exemplo.entidades.Usuario;
import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.PrettyConfigurator;
import com.ocpsoft.pretty.faces.url.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class UsuarioSistemaAutorizadoVoter implements AccessDecisionVoter<Object> {

    private static final String URI_MONITORING = "/monitoring";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    PrettyContext ctx;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Transactional(readOnly = true)
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        Usuario usuarioSistema = (Usuario) authentication.getPrincipal();

        String uri = extractUri((FilterInvocation) object);

        if (usuarioSistema.isAdm()) {
            return ACCESS_GRANTED;
        }
        return ACCESS_DENIED;
    }

    private String extractUri(FilterInvocation filterInvocation) {
        return extractUri(filterInvocation.getHttpRequest()).replaceAll("/faces/", "/");
    }

    private String extractUri(HttpServletRequest request) {
        String uri = request.getServletPath();
        if (request.getPathInfo() != null) {
            return String.format("%s%s", request.getServletPath(), request.getPathInfo());
        } else {
            URL url = new URL(uri);
            if (ctx == null) {
                ctx = PrettyContext.getCurrentInstance(request);
                if (ctx.getConfig().getMappings().isEmpty()) {
                    initPrettyFacesFromServlet(request);
                }
            }
            if (ctx.getConfig().isURLMapped(url)) {
                return getViewID(url);
            }

            return request.getServletPath();
        }
    }

    public String getViewID(URL url) {
        return ctx.getConfig().getMappingForUrl(url).getViewId();
    }

    public void initPrettyFacesFromServlet(HttpServletRequest request) {
        PrettyConfigurator pc = new PrettyConfigurator(request.getServletContext());
        pc.configure();
        ctx.getConfig().setMappings(pc.getConfig().getMappings());
    }

}
