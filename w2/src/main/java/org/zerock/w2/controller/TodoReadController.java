package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.jdbcex.dto.TodoDTO;
import org.zerock.jdbcex.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="todoReadController", urlPatterns = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Long tno = Long.parseLong(req.getParameter("tno"));

            TodoDTO todoDTO = todoService.get(tno);

            //모델 담기
            req.setAttribute("dto", todoDTO);

            /*브라우저는 서버에 요청을 보낼 때마다 자신이 가진 쿠키들을 함께 보냄
            이때 브라우저가 보낸 쿠키 viewTodos 라는 KEY값을 가진 쿠키가 존재하는지 물어본다. */
            //쿠키 찾기
            Cookie viewTodoCookie = findCookie(req.getCookies(), "viewTodos");
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;
            
            // 기존 쿠키의 value가 존재하고, 현재 jsp에서 보여주는 tno가 이전에 접근한 적이 있다면(즉 기록이 남아있다면)
            if(todoListStr != null && todoListStr.indexOf(tno+"-") >= 0 ){
                exist = true; // 이전 기록 존재
            }
            log.info("exist: " + exist);
            
            // 이전 기록이 존재하지 않으면
            if(!exist) {
                // 기존의 값에 새로운 번호로 추가함
                todoListStr += tno+"-";
                //"viewTodos"쿠키에 value로 저장
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60* 60* 24);
                viewTodoCookie.setPath("/");
                resp.addCookie(viewTodoCookie);
            }
            // 브라우저에 응답을 보낼 때 이 쿠키 정보를 추가해서 보냄
            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);

        } catch(Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }
    /*우리가 원하는 쿠키를 브라우저가 전송한 쿠키들에서 찾았으면 반환하고,
    못 찾았으면 */
    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        Cookie targetCookie = null;

        // 브라우저가 보낸 쿠키들이 존재한다면
        if(cookies != null && cookies.length > 0){
            for (Cookie ck:cookies) {
                // 우리가 찾고자 하는 이름과 동일한 쿠키가 존재하면
                if(ck.getName().equals(cookieName)){
                    // 원하는 쿠키 정보를 targetCookie에 저장
                    targetCookie = ck;
                    break;
                }
            }
        }
        // 만약 위에서 쿠키를 찾지 못한다면 targetCookie는 당연히 null이 될 것이다.
        if(targetCookie == null){
            // 브라우저가 쿠키를 가지고 있지 않으므로, 우리가 찾았던 이름의 쿠키를 새로 생성
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24); //유효 기간 1일:60초* 60분* 24시간
        }

        return targetCookie;
    }
}