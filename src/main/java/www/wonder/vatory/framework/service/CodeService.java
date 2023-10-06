package www.wonder.vatory.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import www.wonder.vatory.framework.mapper.CodeMapper;
import www.wonder.vatory.framework.model.CodeVO;

@Service
public class CodeService {
	@Autowired(required = false)
	private CodeMapper codeMapper;

	public List<CodeVO> listAll() {
		return codeMapper.listAll();
	}
}
