package www.wonder.vatory.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.CodeVO;
import www.wonder.vatory.framework.model.RemoconVO;
import www.wonder.vatory.framework.service.CodeService;

@RestController		//Container에 담기도록 지정
@RequestMapping("/framework")
public class CodeController {
	@Autowired
	private CodeService codeService;
	
	// /framework/anonymous/listAllContactPoint
	@GetMapping("/anonymous/listAllContactPointType")
	public ResponseEntity<List<CodeVO>> listAll() {
		List<CodeVO> list = codeService.listAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// /framework/anonymous/getRemoconByName/remocon
	@GetMapping("/anonymous/getRemoconByName/{name}")
	public ResponseEntity<RemoconVO> getRemoconByName(@PathVariable String name) {
		RemoconVO remocon = codeService.getRemoconByName(name);
		return new ResponseEntity<>(remocon, HttpStatus.OK);
	}
}
