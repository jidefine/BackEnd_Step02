package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("login get........");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("login post.........");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");
        String auto = req.getParameter("auto");

        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);
            HttpSession session = req.getSession();
            session.setAttribute("logininfo", memberDTO);
            resp.sendRedirect(("/todo/list"));
        }catch(Exception e) {
            resp.sendRedirect("/login?result=error");
        }

        /*String str = mid+mpw;

        HttpSession session = req.getSession();

        // 임시로 id+pw의 문자열을 loginInfo로 session에 저장
        session.setAttribute("logininfo", str);
        resp.sendRedirect("/todo/list");*/

        boolean rememberMe = auto != null && auto.equals("on");
        if(rememberMe){
            String uuid = UUID.randomUUID().toString();
        }
    }
}
