package www.wonder.vatory.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import www.wonder.vatory.report.model.ReportCodeVO;

@Mapper
public interface ReportMapper {
	public List<ReportCodeVO> listAllReportCodes();
	
}
