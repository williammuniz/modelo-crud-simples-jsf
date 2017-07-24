/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.controladores;

import br.com.exemplo.entidades.Usuario;
import br.com.exemplo.negocios.UsuarioFacade;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author William
 */
//TODO Proteger os arquivos por nível de acesso
public class ServletRecebeUsuarioAlteracaoSenha extends HttpServlet {

    @Autowired
    private UsuarioFacade usuarioFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        try {
            if (!request.getParameter("ticket").equals("")) {
                String codigo = request.getParameter("ticket");
                Usuario u = usuarioFacade.recuperausuarioPorTicketAlteracaoSenha(codigo);
                if (u != null) {
                    request.getSession().setAttribute("usuarioAlteracaoSenha", u);
                    response.sendRedirect("/eprocon/faces/alterasenha.xhtml");
                } else {
                    response.sendRedirect("/eprocon/erro404.jsp");
//                    out.write("SEM USUARIO".getBytes());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServletRecebeUsuarioAlteracaoSenha.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
