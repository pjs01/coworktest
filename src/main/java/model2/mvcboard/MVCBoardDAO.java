package model2.mvcboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool{
	//기본생성자. 생성자가 호출되면 부모 클래스의 생성자가 호출된다.
	public MVCBoardDAO() {}	
	//전체 글의 수
	public int selectCount(Map<String,Object> map) {
		int totalCount=0;
		String query="select count(*) from mvcboard ";
		//검색조건추가
		if(map.get("searchWord")!=null) {
			query+=" where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%'";
		}
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			if(rs.next()) {
				totalCount=rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}
	//목록.페이징처리
	public List<MVCBoardDTO> selectListPage(Map<String,Object> map){
		List<MVCBoardDTO> board=new ArrayList<MVCBoardDTO>();
		// 쿼리문 작성
		String query="select * from ( "
				+ "		select tb.*, rownum rnum from ( "
				+ "			select * from mvcboard ";
		//검색조건추가
		if(map.get("searchWord")!=null) {
			query+="		where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%'";
		}
				
		query+="			order by idx desc "
				+ "		) tb "
				+ "	) "
				+ " where rnum between ? and ?";	
		try {
			psmt=con.prepareStatement(query); // prepared statement객체 생성
			psmt.setString(1, map.get("start").toString()); // 첫번째 ?에 값 매핑
			psmt.setString(2, map.get("end").toString()); // 두번째 ?에 값 매핑
			rs=psmt.executeQuery();
			while(rs.next()) {
				//DTO에 쿼리결과를 저장
				MVCBoardDTO dto=new MVCBoardDTO();
				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setPass(rs.getString("pass"));
				dto.setVisitcount(rs.getInt("visitcount"));
				
				board.add(dto); // list에 추가	
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return board; // list를 리턴
	}
	//등록
	public int insertWrite(MVCBoardDTO dto) {
		int result=0; // 영향을 받은 행의 수
		
		try {
			String query="insert into mvcboard ( "
					+ " idx,name,title,content,ofile,sfile,pass) "
					+ " values( seq_board_num.nextval,?,?,?,?,?,? )";
			psmt=con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			result=psmt.executeUpdate(); //executeUpdate()의 리턴값은 영향을 받은 행의 수
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result; // 영향을 받은 행의 수 리턴
	}
	//다운로드횟수증가
	public void downCountPlus(String idx) {
		String sql="update mvcboard set downcount=downcount+1 where idx=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//상세보기
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto=new MVCBoardDTO(); // dto생성
		String query="select * from mvcboard where idx=?"; // idx로 검색
		try {
			psmt=con.prepareStatement(query);
			psmt.setString(1, idx);
			rs=psmt.executeQuery();
			if(rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto; //dto리턴
	}
	//조회수증가
	public void updateVisitCount(String idx) {
		String sql="update mvcboard set visitcount=visitcount+1 where idx=?";
		try {
			psmt=con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//비밀번호확인
	public boolean confirmPassword(String pass,String idx) {
		boolean isCorr=true; //비밀번호일치 여부 확인 flag
		try {
			String sql="select count(*) from mvcboard where pass=? and idx=?";
			psmt=con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs=psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0){ // count값이 0이면
				isCorr=false; // flag값 false로 변경
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isCorr;
	}
	//삭제
	public int deletePost(String idx) {
		int result=0;
		try {
			String query="delete from mvcboard where idx=?";
			psmt=con.prepareStatement(query);
			psmt.setString(1, idx);
			result=psmt.executeUpdate();//영향을 받은 행의 수 저장
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//수정
	public int updatePost(MVCBoardDTO dto) {
		int result=0;
		try {
			String query="update mvcboard set title=?,name=?,content=?,ofile=?,sfile=? "
					+ " where idx=? and pass=?";
			psmt=con.prepareStatement(query);
			psmt.setString(1,dto.getTitle());
			psmt.setString(2,dto.getName());
			psmt.setString(3,dto.getContent());
			psmt.setString(4,dto.getOfile());
			psmt.setString(5,dto.getSfile());
			psmt.setString(6,dto.getIdx());
			psmt.setString(7,dto.getPass());
			result=psmt.executeUpdate(); //영향을 받은 행의 수 저장
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
}
