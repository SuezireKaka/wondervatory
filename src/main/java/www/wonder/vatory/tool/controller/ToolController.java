package www.wonder.vatory.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.tool.service.ToolService;

@RestController	
@CrossOrigin
@RequestMapping("/party")
public class ToolController {
	@Autowired
	private ToolService toolService;
}
