package www.wonder.vatory.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.elasticsearch.model.result.ElasticResultVO;
import www.wonder.vatory.elasticsearch.service.ElasticService;

@RestController
@CrossOrigin
@RequestMapping("/elastic")
public class ElasticController {
	@Autowired
	ElasticService elasticService;
	
	// /elastic/getLatestReadOf/0056/7/sex_male-age_any
	@GetMapping("/getLatestReadOf/{workId}/{daynum}/{condi}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer', 'manager', 'admin')")
	public ResponseEntity<ElasticResultVO> getLatestReadOf(@PathVariable String workId,
			@PathVariable int daynum, @PathVariable String condi) {
		ElasticResultVO result = elasticService.getLatestReadOfSeries(workId, daynum, condi);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// /elastic/getLatestReadByEpinum/0056/1/7/sex_male-age_any
	@GetMapping("/getLatestReadByEpinum/{seriesId}/{epinum}/{daynum}/{condi}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer', 'manager', 'admin')")
	public ResponseEntity<ElasticResultVO> getLatestReadByEpinum(@PathVariable String seriesId,
			@PathVariable int epinum, @PathVariable int daynum, @PathVariable String condi) {
		ElasticResultVO result = elasticService.getLatestReadOfEpinum(seriesId, epinum, daynum, condi);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
