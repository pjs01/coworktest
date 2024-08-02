package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

/**
 * Servlet implementation class PassController
 */
@WebServlet("/pass.do")
public class PassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// url parameter "mode"값을 읽어서 request객체에 "mode"라는 attribute name을 만들어서 저장
		request.setAttribute("mode", request.getAttribute("mode"));
		request.getRequestDispatcher("/Pass.jsp").forward(request, response);//포워딩.주소바뀌지 않음.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idx=request.getParameter("idx");
		String mode=request.getParameter("mode");
		String pass=request.getParameter("pass");
		
		MVCBoardDAO dao=new MVCBoardDAO();
		boolean confirmed=dao.confirmPassword(pass, idx);//비밀번호 확인
		dao.close();
		if(confirmed) {
			if(mode.equals("edit")) {
				HttpSession session=request.getSession(); //session객체생성
				session.setAttribute("pass",pass);// session attribute생성
				response.sendRedirect("/edit.do?idx="+idx);// 주소가 변경됨
			}else if(mode.equals("delete")){
				dao=new MVCBoardDAO();
				MVCBoardDTO dto=dao.selectView(idx);//상세정보 dto에 저장
				int result=dao.deletePost(idx);//삭제처리
				dao.close();
				if(result==1) {//delete문이 성공했으면
					String saveFileName=dto.getSfile();//저장된파일명
					FileUtil.deleteFile(request,"/Uploads",saveFileName);//파일삭제
				}
				JSFunction.alertLocation(response, "삭제되었습니다", "/list.do");			
			}
		}else {
			JSFunction.alertBack(response, "비밀번호가 일치하지 않습니다.");
		}
	}

}
