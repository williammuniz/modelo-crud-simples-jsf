/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exemplo.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author William
 */
public class EmailUtil {

    private static final String ADDRESS = "contatotechcode@gmail.com";
    private static final String PASSWORD = "@techcode@";
//    public static void enviaEmailComAnexo(String email, String mensagem, String assunto, final Arquivo... arquivos) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "pod51028.outlook.com");
//        props.put("mail.smtp.port", 587);
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "false");
//        Session session = Session.getInstance(props);
//        try {
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress("contato@privatepay.com.br"));
//            Multipart mp = new MimeMultipart();
//            // cria a primeira parte da mensagem
//            MimeBodyPart mbp1 = new MimeBodyPart();
//            mbp1.setText(mensagem);
//            mp.addBodyPart(mbp1);
//            if (arquivos != null) {
//                for (final Arquivo arquivo : arquivos) {
//                    MimeBodyPart mbp2 = new MimeBodyPart();
//                    DataSource fds = new DataSource() {
//                        public InputStream getInputStream() throws IOException {
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            Arquivo ar2 = Persistencia.getTlem().get().find(Arquivo.class, arquivo.getId());
//                            for (ArquivoParte ap : ar2.getPartes()) {
//                                baos.write(ap.getDados());
//                            }
//                            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//                            return bais;
//                        }
//
//                        public OutputStream getOutputStream() throws IOException {
//                            throw new UnsupportedOperationException("Not supported yet.");
//                        }
//
//                        public String getContentType() {
//                            return arquivo.getMimeType();
//                        }
//
//                        public String getName() {
//                            return arquivo.getNome();
//                        }
//                    };
//                    mbp2.setDataHandler(new DataHandler(fds));
//                    mbp2.setFileName(fds.getName());
//                    mp.addBodyPart(mbp2);
//                }
//            }
//            msg.setContent(mp);
//            InternetAddress[] address = {new InternetAddress(email)};
//            msg.setRecipients(Message.RecipientType.TO, address);
//            msg.setSubject(assunto);
//            msg.setSentDate(new Date());
//            Transport t = session.getTransport("smtp");
//            try {
//                t.connect("pod51028.outlook.com", "contato@privatepay.com.br", "Ppay102030");
//                t.sendMessage(msg, msg.getAllRecipients());
//            } finally {
//                t.close();
//            }
//        } catch (Exception mex) {
//            throw new RuntimeException(mex.getMessage());
//        }
//    }

    public static boolean enviaEmailSenha(String emailDestino, String mensagem, String assunto) {
        Properties props = getProperties();
        Session session = Session.getInstance(props);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(ADDRESS));
            Multipart mp = new MimeMultipart();
            // cria a primeira parte da mensagem
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(mensagem);
            mp.addBodyPart(mbp1);

            InternetAddress[] address = {new InternetAddress(emailDestino)};
            msg.setRecipients(Message.RecipientType.TO, address);

            msg.setSubject(assunto);
            msg.setSentDate(new Date());
            Transport t = session.getTransport("smtp");
            try {
//                t.connect("pod51028.outlook.com", "contato@privatepay.com.br", "Ppay102030");
                t.connect(ADDRESS, PASSWORD);
                t.sendMessage(msg, msg.getAllRecipients());
                return true;
            } finally {
                t.close();
            }
        } catch (Exception mex) {
            mex.printStackTrace();
            throw new RuntimeException(mex.getMessage());
        }
    }

    public static void enviaEmailHtml(String email, String mensagem, String assunto) {
        Properties props = getProperties();
        Session session = Session.getInstance(props);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(ADDRESS));
            mensagem = montaCorpoEmailPadrao(mensagem);
            msg.setContent(mensagem, "text/html; charset=UTF-8");
            InternetAddress[] address = {new InternetAddress(email)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(assunto);
            msg.setSentDate(new Date());
            Transport t = session.getTransport("smtp");
            try {
//                t.connect("pod51028.outlook.com", "contato@privatepay.com.br", "Ppay102030");
                t.connect(ADDRESS, PASSWORD);
                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                t.close();
            }
        } catch (Exception mex) {
            mex.printStackTrace();
            throw new RuntimeException(mex.getMessage());
        }
    }

    public static String montaCorpoEmailPadrao(String mensagem) {
        String corpo = "<html xmlns=\"http://www.w3.org/1999/xhtml\""
                + ">"
                + " <head>"
                + " <style type=\"text/css\">"
                + " *{"
                + " padding: 0;"
                + " }"
                + " .bordaBox {background: transparent; width:30%;}"
                + " .bordaBox .b1, .bordaBox .b2, .bordaBox .b3, .bordaBox .b4, .bordaBox .b1b, .bordaBox .b2b, .bordaBox .b3b, .bordaBox .b4b {display:block; overflow:hidden; font-size:1px;} "
                + " .bordaBox .b1, .bordaBox .b2, .bordaBox .b3, .bordaBox .b1b, .bordaBox .b2b, .bordaBox .b3b {height:1px;} "
                + " .bordaBox .b2, .bordaBox .b3, .bordaBox .b4 {background:#ffffff; border-left:1px solid #999; border-right:1px solid #999;}"
                + " .bordaBox .b1 {margin:0 5px; background:#999;}"
                + " .bordaBox .b2 {margin:0 3px; border-width:0 2px;}"
                + " .bordaBox .b3 {margin:0 2px;}"
                + " .bordaBox .b4 {height:2px; margin:0 1px;}"
                + "  .bordaBox .conteudo {padding:5px;display:block; background:#ffffff; border-left:1px solid #999; border-right:1px solid #999;}"
                + " </style>"
                + "  <title>Private Pay</title>"
                + "   </head>"
                + " <body style=\"font-family: arial, helvetica, serif; color: #363636; background: #ffffff repeat-x;margin:0; padding:0;\" class=\"teste\"  ${param['cliente']=='sim'?'onload=\"document.forms[0].submit()\"': ''}>"
                + "  <div align=\"center\" class=\"bordaBox\" style=\"width: 80%; margin-left: 10%; margin-top: 10%;\">"
                + "    <b class=\"b1\"></b><b class=\"b2\"></b><b class=\"b3\"></b><b class=\"b4\"></b>"
                + "   <div class=\"conteudo\" style=\"min-height: 200px; padding-left: 5%; padding-right: 5%\" >"
                + "      <div>                    "
                + "            <img src=\"http://www.eprocon.com.br/eprocon/images/brand-pp.gif\" "
                + "                  alt=\"E-Procon\" "
                + "         />"
                + "    <h2>Comunicado Digital</h2>"
                + mensagem
                + " <p style=\"font-size: small\">Atenciosamente, TechCode</p>"
                + "   </div>"
                + "   </div>"
                + "   <b class=\"b4\"></b><b class=\"b3\"></b><b class=\"b2\"></b><b class=\"b1\"></b>"
                + "  </div>"
                + "  <div align=\"center\" style=\"margin-top: 8%; height: 100px;\">"
                + "     <h:outputText value=\"Proteção e Defesa ao Consumidor\"/>"
                + "     <br/><br/>"
                + "  </div>"
                + "   <div style=\"width: 100%; height: 75px; background: #144257; margin:0 auto;\">"
                + "     <div align=\"center\" style=\"width: 50%; height: 75px; margin-left: 25%; background: #214e62; color: #999999; font-size: small\">"
                + "        <br/>"
                + "         <p>Todos os direitos reservados. Copyright 2012, TechCode.</p>"
                + "     </div>"
                + "   </div>"
                + "  </body>"
                + " </html>";

        return corpo;
    }

    public static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", "true");
        return props;
    }
}
