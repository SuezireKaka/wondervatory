package www.wonder.vatory.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.tool.mapper.ToolMapper;

@Service
public class WorkService {
	@Autowired(required = false)
	private ToolMapper toolMapper;


}
