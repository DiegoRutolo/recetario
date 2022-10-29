package eu.rutolo.recetario.security;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {
	private final Logger logger = LoggerFactory.getLogger(FilesService.class);
	
	public Path saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			logger.info("Guardado archivo en %s", filePath.toString());
			return filePath;
		} catch (IOException ioe) {
			logger.error("Error guardando archivo", ioe);
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}
}
