package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
@WebFilter("/CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {
	//字符编码（初始化参数）
	protected String encoding = null;
    protected FilterConfig fConfig = null;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		this.encoding = null;
		this.fConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(this.encoding != null){
        	request.setCharacterEncoding(this.encoding);
        	response.setContentType("text/html;charset="+this.encoding);
        }
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.fConfig = fConfig;
		this.encoding = fConfig.getInitParameter("encoding");
	}

}
