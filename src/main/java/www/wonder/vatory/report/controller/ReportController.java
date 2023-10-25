package www.wonder.vatory.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;
import www.wonder.vatory.report.service.ReportService;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private ReportService reportService;

	// /report/anonymous/listAllReportCodes
	@GetMapping("/anonymous/listAllReportCodes")
	public ResponseEntity<List<ReportCodeVO>> listAllReportCodes() {
		List<ReportCodeVO> list = reportService.listAllReportCodes();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// /report/anonymous/listAllReports/1
	@GetMapping("/anonymous/listAllReports/{page}")
	public ResponseEntity<DreamPair<List<ReportVO>, PagingDTO>> listAllReports(
			@PathVariable int page) {

		DreamPair<List<ReportVO>, PagingDTO> list = reportService.listAllReports(page);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
