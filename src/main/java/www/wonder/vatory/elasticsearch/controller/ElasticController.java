package www.wonder.vatory.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.elasticsearch.service.ElasticService;

@RestController
@CrossOrigin
@RequestMapping("/elastic")
public class ElasticController {
	@Autowired
	ElasticService elasticService;
	
	
	// /elastic/anonymous/listLatestReadOf/0056/7/sex_male-age_any
	@GetMapping("/anonymous/listLatestReadOf/{workId}/{daynum}/{condi}")
	public ResponseEntity<Object> listLatestRead(@PathVariable String workId,
			@PathVariable int daynum, @PathVariable String condi) {
		Object list = elasticService.listLatestRead(workId, daynum, condi);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
