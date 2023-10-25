package www.wonder.vatory.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.MetaMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;

@Mapper
public interface ReportMapper extends MetaMapper {
	public List<ReportCodeVO> listAllReportCodes();

	public List<ReportVO> listAllReports(@Param("paging") PagingDTO paging);

	public List<ReportCodeVO> listAllTypesOf(@Param("id") String id);
	
}
