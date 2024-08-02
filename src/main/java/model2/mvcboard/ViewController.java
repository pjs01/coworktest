package model2.mvcboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewController
 */
@WebServlet("/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MVCBoardDAO dao=new MVCBoardDAO(); //dao생성
		String idx=request.getParameter("idx"); //글번호
		dao.updateVisitCount(idx); //조회수증가
		MVCBoardDTO dto=dao.selectView(idx); //상세정보
		dao.close();
		//줄바꿈문자를 <br>로 변경
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br>"));
		request.setAttribute("dto", dto); // attribute name "dto"에 dto를 저장
		request.getRequestDispatcher("/View.jsp").forward(request, response); // 포워딩. 주소 변경 안됨.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
