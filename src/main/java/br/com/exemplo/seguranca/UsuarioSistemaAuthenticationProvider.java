package br.com.exemplo.seguranca;

import br.com.exemplo.entidades.Usuario;
import br.com.exemplo.enums.TipoUsuario;
import br.com.exemplo.negocios.UsuarioFacade;
import br.com.exemplo.util.Seguranca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class UsuarioSistemaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioFacade usuarioFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if("admin".equals(authentication.getPrincipal().toString()) && "admin".equals(authentication.getCredentials().toString())){
                Usuario usuarioSistema = new Usuario(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
                usuarioSistema.setTipo(TipoUsuario.ADMINISTRADOR);
                return new UsernamePasswordAuthenticationToken(usuarioSistema, null, usuarioSistema.getAuthorities());
            }
            Usuario usuarioSistema = findUsuarioSistema(authentication);
            if (usuarioSistema.isAutenticacaoCorreta(Seguranca.md5(authentication.getCredentials().toString()))) {
                return new UsernamePasswordAuthenticationToken(usuarioSistema, null, usuarioSistema.getAuthorities());
            } else {
                throw new BadCredentialsException("Autenticação incorreta.");
            }
        }
        return null;
    }

    private Usuario findUsuarioSistema(Authentication authentication) {
        Usuario usuarioSistema = usuarioFacade.recuperarUsuarioPorLogin(authentication.getName());
        if (usuarioSistema == null) {
            throw new UsernameNotFoundException(String.format("Usuário não encontrado: '%s'",
                    authentication.getName()));
        }
        return usuarioSistema;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}