package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Login Check Filter....");

        // 로그인 정보가 세션에 존재하는지 여부를 판다
        HttpServletRequest req = (HttpServletRequest) servletrequest;
        HttpServletResponse resp = (HttpServletResponse) servletresponse;

        HttpSession session = req.getSession();

        // 세션에 로그인 정보가 있으니 로그인이라고 판단하고 서블릿으로 전송
        if(session.getAttribute("logininfo") !==null){

            // 다음 필터 또는 서블릿으로 전달
            filterChain.doFilter(servletRequest, servletResponse);\

            return;
        }

        // 세견에 loginInfo는 없지만, 브라우저에서 remember-me를 보냈는지 찾는다.
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // remember-me가 없으면, login하라고 /login을 보낸다.
        if(cookie == null){
            resp.sendRedirect("/login");
            return;
        }

        log.info("cookie는 존재하는 상황");

        //remember-me의 value를 추출
        String uuid = cookie.getValue();

        try{
            //브라우저의 remember-me 쿠키값과 Maria-DB의 uuid 값이 일치하는지
            MemberDTO memberDTO= MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO);

            // remember-me의 정보가 Maria-DB의 uuid와 일치하지 않는다.
            if(memberDTO == null){
                throw new Exception("Cookie value is not vaild");
            }

            //쿠키값이 DB값과 일치하면, loginInfo를 세션에 저장하고 서블릿 전송
            session.setAttribute("loginInfo", memberDTO);
            filterChain.doFilter(servletRequest, servletResponse);
        }catch(Exception e){
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name){
        if(cookies == null || cookies.length == 0) return null;\
        
        // Cookie 객체를 얻는데, 추가로 Optional에서 제공하는 메서드를 사용할 수 있다
        Optional<Cookie> result = Arrays.stream(cookies)
            // 람다식 : "ck"라는 매개변수를 받고, 해당 매개변수의 getName() 메서드를 호출하여 그 결과가 "name"과 같은지 비교
            .filter(ck -> ck.getName().equals(name))
            .findFirst();

        //result가 정상이면 Cookie 객체를 리턴하고 아니면 null값을 리턴한다
        // 삼항 조건 연산자
        return result.isPresent() ? result.get() : null;
    }
}
