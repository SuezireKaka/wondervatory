package www.wonder.vatory.fileattachment.model.dto;

import java.io.File;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.wonder.vatory.fileattachment.model.PlaybleContentTypes;
import www.wonder.vatory.fileattachment.service.AttachFileCleaner;

@Entity
@Table(name="t_attach")
@NoArgsConstructor
@Getter
@Setter
public class AttachFileDTO {
	public static final String THUMBNAIL_FILE_PREFIX = "thumb_";
	public static final String THUMBNAIL_FILE_POSTFIX = ".png";

	@Id
	private String uuid;

	private String ownerType;
	private String ownerId;

	//서버에서 관리된 경로 정보
	@Column(name="path")
	private String pathName;
	
	//원본 파일 이름. 화면에 출력 용도
	//c:\sss/bb/aaa.txt => aaa.txt
	@Column(name="name")
	private String originalFilePureName;

	@Column(name="type_name")
    private PlaybleContentTypes contentType;

	public AttachFileDTO(String pathName, String originalFilePureName, String uuid) {
		this.originalFilePureName = originalFilePureName;
		this.pathName = pathName;
		this.uuid = uuid;
	}

	public File findThumnailFile(String uploadDir) {
		return new File(uploadDir + File.separator + convertToPath() + File.separator + thumbFileName());
	}
	
	public File findUploadedFile(String uploadDir) {
		return new File(uploadDir + File.separator + convertToPath() + File.separator + pureFileName());
	}

	public void setContentType(PlaybleContentTypes contentType) {
		this.contentType = contentType;
	}

	public void deleteUploadedFiles(String uploadDir) {
		findUploadedFile(uploadDir).delete();
		findThumnailFile(uploadDir).delete();
	}
	
	private String convertToPath() {
		return pathName.replace(AttachFileCleaner.DATE_STRING_DELIMETER, File.separator.charAt(0));
	}
	
	public String pureFileName() {
		return uuid + '_' + originalFilePureName;
	}
	
	public String thumbFileName() {
		return THUMBNAIL_FILE_PREFIX + uuid + THUMBNAIL_FILE_POSTFIX;
	}
	
	public boolean hasThumbnail() {
		return contentType.isThumbnailTarget();
	}
}
