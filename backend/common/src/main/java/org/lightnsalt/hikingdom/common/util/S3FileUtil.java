package org.lightnsalt.hikingdom.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3FileUtil {
	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// 이미지 업로드 method
	public String upload(MultipartFile multipartFile, String dirName) throws IOException {
		// 파일 업로드
		// log.info("file : {}, dirName : {}", multipartFile, dirName);
		File uploadFile = convertToFile(multipartFile)
			.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 변환에 실패했습니다."));
		// 파일명 중복을 피하기 위해 회원 정보 추가
		String fileName = dirName + " " + LocalDateTime.now();
		log.info("created fileName : {}", fileName);

		// put - S3로 업로드
		String uploadImageUrl = putS3(uploadFile, fileName);
		// 로컬 파일 삭제
		removeFile(uploadFile);

		return uploadImageUrl;
	}

	// 이미지 삭제 method
	public boolean delete(String url, String path) {
		// S3에서 삭제
		log.info("profile url : {}", url);
		Pattern tokenPattern = Pattern.compile("(?<=" + path + "/).*");
		Matcher matcher = tokenPattern.matcher(url);

		String temp = "";
		if (matcher.find()) {
			temp = matcher.group();
		}

		String originalName = URLDecoder.decode(temp, StandardCharsets.UTF_8);
		String filePath = "profile/" + originalName;
		log.info("originalName : {}", originalName);
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
			log.info("deletion complete : {}", filePath);
			return true;
		} catch (SdkClientException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	// 로컬 파일 삭제
	private void removeFile(File targetFile) {
		if (targetFile.exists()) {
			if (targetFile.delete()) {
				log.info("파일이 삭제되었습니다.");
			} else {
				log.info("파일이 삭제되지 않았습니다.");
			}
		}
	}

	// S3로 파일 업로드 및 파일 url 리턴
	private String putS3(File uploadFile, String fileName) {
		amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
			.withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3.getUrl(bucket, fileName).toString();
	}

	// multipartFile -> File 형식으로 변환 및 로컬에 저장
	private Optional<File> convertToFile(MultipartFile file) throws IOException {
		File uploadFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		FileOutputStream fos = new FileOutputStream(uploadFile);
		fos.write(file.getBytes());
		fos.close();

		return Optional.of(uploadFile);
	}

}
