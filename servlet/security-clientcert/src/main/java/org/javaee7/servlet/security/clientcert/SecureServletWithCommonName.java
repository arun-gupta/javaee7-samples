/** Copyright Payara Services Limited **/
package org.javaee7.servlet.security.clientcert;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cuba Stanley
 */
@WebServlet(urlPatterns = { "/SecureServletWithCommonName" })
public class SecureServletWithCommonName extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commonName = "";
        
        String[] certComponents = request.getUserPrincipal().toString().split(",");
        for(String component : certComponents) {
            if(component.contains("CN=")) {
                commonName = component.trim().replace("CN=", "");
            }
        }
        
        response.getWriter().print("principal common name " + commonName + " in role g2:" + request.isUserInRole("g2"));
    }
}
