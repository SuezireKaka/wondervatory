package www.wonder.vatory.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.fileattachment.service.AttachFileService;
import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.report.mapper.ReportMapper;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;


@Service
public class ReportService {
	@Autowired(required = false)
	private ReportMapper reportMapper;
	@Autowired
	private AttachFileService attachFileService;

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
			List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(report);
			report.setListAttachFile(attachFileList);
		}
		
		return new DreamPair<List<ReportVO>, PagingDTO>(listResult, paging);
	}

	public int manageReport(AccountVO reporter, ReportVO report) {
		//child.id가 없으면 제작
		int cnt = 0;
		if (ObjectUtils.isEmpty(report.getId())) {
			report.setReporter(reporter);
			cnt += reportMapper.createReport(report);
			attachFileService.createAttachFiles(report);
		}
		//child.id가 있으면 수정
		else {
			attachFileService.deleteAttachFiles(report);
			cnt += reportMapper.updateReport(report);
			attachFileService.createAttachFiles(report);
		}
		
		cnt &= syncRptTypes(report.getId(), report.getRptTypesList());
		return cnt;
	}

	private int syncRptTypes(String reportId, List<ReportCodeVO> rptTypesList) {
		int result = 0;
		int requestCount = rptTypesList.size();
		// 현재 들어있는 개수랑 비교해서 판단
		int prevCount = reportMapper.countRptTypesOf(reportId);
		
		if (requestCount > prevCount) {
			// 늘어났으면 prevCount만큼 리스트를 분할해서 업데이트 이후 추가
			int border = prevCount;
			
		    List<List<ReportCodeVO>> listOfLists = new ArrayList<>(
		    		rptTypesList.stream()
			    	.collect(Collectors.groupingBy(s -> rptTypesList.indexOf(s) >= border))
			    	.values()
		    );
		    // 둘로 쪼개는데 한 쪽이 없으면 알잘딱깔센 [[], [0, 1, 2]] 해야 할 거 아니야 ㅡ.ㅡ
		    // 근데 못 해서 일단 마지막을 insertList로 두고
		    int size = listOfLists.size();
		    List<ReportCodeVO> insertList = listOfLists.get(size - size);
		    
		    //사이즈에 따라 updateList를 다르게
		    if (listOfLists.size() == 1) {
		    	result = reportMapper.insertToSync(reportId, 0, insertList);
		    }
		    else {
		    	List<ReportCodeVO> updateList = listOfLists.get(0);
		    	result = reportMapper.updateAllRptFrom(reportId, updateList)
		    		& reportMapper.insertToSync(reportId, updateList.size(), insertList);
		    }
		    
		}
		else {
			result = reportMapper.deleteToSync(reportId, requestCount)
				& reportMapper.updateAllRptFrom(reportId, rptTypesList);
		}
		
		return result;
	}


}
