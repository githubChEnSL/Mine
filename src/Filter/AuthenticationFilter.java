package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AuthenticationFilter过滤器 实现用户身份验证
 * 
 * @author chenshaolei 2019年11月27日 上午11:48:13
 */
public class AuthenticationFilter implements Filter {

	// 定义Log4j日志
	private static Logger logger = LogManager.getLogger(Class.class);

	/**
	 * 重写doFilter，完成身份验证
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		logger.info("身份验证");
		HttpServletRequest request = (HttpServletRequest) arg0;// 获取request对象
		HttpServletResponse response = (HttpServletResponse) arg1;
		try {
			HttpSession session = request.getSession();// 获取session对象
			if (session.getAttribute("id") != null) {
				//System.out.println("身份验证-----在线中,用户为:" + session.getAttribute("id"));
				arg2.doFilter(arg0, arg1);
			} else {
				logger.error("登录状态异常,请重新登陆");
				request.getSession().setAttribute("LoginMsg", "登录状态异常,请重新登陆");
				response.sendRedirect("login.jsp");
				//System.out.println("身份验证-----离线中,请重新登陆");
			}
		} catch (Exception e) {
			logger.warn("登录状态异常,请重新登陆");
			request.getSession().setAttribute("LoginMsg", "登录状态异常,请重新登陆");
			response.sendRedirect("login.jsp");
			//System.out.println("身份验证-----离线中,请重新登陆");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
