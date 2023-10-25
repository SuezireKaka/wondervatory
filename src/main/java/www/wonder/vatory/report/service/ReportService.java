package www.wonder.vatory.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.ContactPointVO;
import www.wonder.vatory.party.model.PartyVO;
import www.wonder.vatory.report.mapper.ReportMapper;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;
import www.wonder.vatory.work.model.SeriesVO;


@Service
public class ReportService {
	@Autowired(required = false)
	private ReportMapper reportMapper;

	public List<ReportCodeVO> listAllReportCodes() {
		return reportMapper.listAllReportCodes();
	}

	public DreamPair<List<ReportVO>, PagingDTO> listAllReports(int page) {
		PagingDTO paging = new PagingDTO(page);
		List<ReportVO> listResult = reportMapper.listAllReports(paging);
		long dataCount = reportMapper.getFoundRows();
		paging.buildPagination(dataCount);
		
		for (ReportVO report : listResult) {
			List<ReportCodeVO> types = reportMapper.listAllTypesOf(report.getId());
			report.setRptTypesList(types);
		}
		
		return new DreamPair<List<ReportVO>, PagingDTO>(listResult, paging);
	}


}
