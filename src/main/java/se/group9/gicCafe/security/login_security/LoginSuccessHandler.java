package se.group9.gicCafe.security.login_security;
 
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.group9.gicCafe.constants.CONSTANT;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
         
        String redirectURL = request.getContextPath();
         
        if (userDetails.hasRole(CONSTANT.Admin_Role)) {
            redirectURL = "admin/cashiers";
        } else if (userDetails.hasRole(CONSTANT.Cashier_Role)) {
            redirectURL = "tables";
        }

        response.sendRedirect(redirectURL);
    }
 
}