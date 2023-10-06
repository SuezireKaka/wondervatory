package www.wonder.vatory.tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.tool.mapper.ToolMapper;

@Service
public class ToolService {
	@Autowired(required = false)
	private ToolMapper toolMapper;


}
