package www.wonder.vatory.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.report.mapper.ReportMapper;
import www.wonder.vatory.report.model.ReportCodeVO;


@Service
public class ReportService {
	@Autowired(required = false)
	private ReportMapper reportMapper;

	public List<ReportCodeVO> listAllReportCodes() {
		return reportMapper.listAllReportCodes();
	}


}
