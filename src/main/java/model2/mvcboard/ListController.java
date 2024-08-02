package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BoardPage;

/**
 * Servlet implementation class ListController
 */
@WebServlet("/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DAO 생성. MVCBoardDAO는 DBConnPool의 자식이므로 DBConnPool의 생성자를 통해서 DB연결.
		MVCBoardDAO dao = new MVCBoardDAO();
		
		//Map생성
		Map<String, Object> map=new HashMap<String, Object>();//upcasting
		
		//검색필드, 검색어 map에 저장
		String searchField=request.getParameter("searchField");
		String searchWord=request.getParameter("searchWord");
		if(searchWord!=null) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		
		//전체글의 수
		int totalCount=dao.selectCount(map);
		//페이지당 글의 수
		int pageSize=10;
		//페이지블록의 크기. 1 2 3 4 5 식으로 5개씩 출력.
		int blockPage=5; 
		
		//페이지번호구하기
		int pageNum=1; //기본값. 1 페이지
		String pageTemp=request.getParameter("pageNum");
		if(pageTemp!=null && !pageTemp.equals("")) { // pageTemp값이 있으면
			pageNum=Integer.parseInt(pageTemp); 
		}		
		
		int start=(pageNum-1)*pageSize+1;
		int end=pageNum*pageSize;
		map.put("start", start);
		map.put("end", end);
		
		List<MVCBoardDTO> boardLists=dao.selectListPage(map); //목록을 구해서 boardList에 저장
		dao.close();// DB연결 닫기.
		
		// 페이지블록 생성
		String pagingImg=BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "/list.do");
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		
		// 목록을 jsp페이지로 전달
		request.setAttribute("boardLists", boardLists);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/List.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
