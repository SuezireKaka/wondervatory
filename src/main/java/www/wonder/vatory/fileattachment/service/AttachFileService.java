package www.wonder.vatory.fileattachment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.fileattachment.repository.AttachFileRepository;
import www.wonder.vatory.framework.model.interfaces.MappedTableDef;

@Service
public class AttachFileService {
    
	@Autowired
	private AttachFileRepository attachFileRepository;

	public List<AttachFileDTO> getAttachFileList(MappedTableDef owner) {
		List<AttachFileDTO> ret = attachFileRepository.findByOwnerTypeAndOwnerId(owner.getMappedTableName(), owner.getId());
		return ret;
	}
	
	public void createAttachFiles(MappedTableDef owner) {
		List<AttachFileDTO> list = owner.getListAttachFile();
		if (list == null)
			return;
		list.forEach(e->{
			e.setOwnerType(owner.getMappedTableName());
			e.setOwnerId(owner.getId());
		});
		
		attachFileRepository.saveAll(list);
	}

	public void deleteAttachFiles(MappedTableDef owner) {
		attachFileRepository.deleteAllByOwnerTypeAndOwnerId(owner.getMappedTableName(), owner.getId());
	}

}
