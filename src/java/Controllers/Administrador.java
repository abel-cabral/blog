/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.dao.DaoFactory;
import Model.dao.UsuarioDao;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.entities.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Administracao", urlPatterns = {"/Administracao"})
public class Administrador extends HttpServlet {

    UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer id = 0;
        try {
            id = Integer.parseInt((String) session.getAttribute("id"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        List<Usuario> usuarios = usuarioDao.findAll(id);
        System.out.println("Acessou a pagina de controle do adm");
        request.setAttribute("usuarios", usuarios);
        response.sendRedirect(request.getContextPath() + "/administracao.jsp");
    }
}