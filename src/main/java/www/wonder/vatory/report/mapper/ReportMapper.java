package www.wonder.vatory.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;

@Mapper
public interface ReportMapper extends WonderMapper {
	public List<ReportCodeVO> listAllReportCodes();

	public List<ReportVO> listAllReports(@Param("paging") PagingDTO paging);

	public List<ReportCodeVO> listAllTypesOf(@Param("id") String id);
	
	public int countRptTypesOf(String id);

	public int createReport(ReportVO report);
	public int insertToSync(@Param("reportId") String reportId, @Param("offset") int offset,
			@Param("insertList") List<ReportCodeVO> insertList);

	public int updateReport(@Param("report") ReportVO report);
	public int updateAllRptFrom(@Param("reportId") String reportId,
			@Param("updateList") List<ReportCodeVO> updateList);

	public int deleteToSync(@Param("reportId") String reportId,
			@Param("border") int border);

	

	
	
}
