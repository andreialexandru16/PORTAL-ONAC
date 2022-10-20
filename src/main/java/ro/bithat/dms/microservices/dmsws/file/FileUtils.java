package ro.bithat.dms.microservices.dmsws.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 *
 */
public final class FileUtils {
	public final Map<String, String> paramMap = new HashMap<>();
	public final List<UploadFileDescription> uploadedFiles = new ArrayList<>();


	public void putParam(String name, String value) {
		paramMap.put(name, value);
	}
	public void addUploadedFile(UploadFileDescription file) {
		uploadedFiles.add(file);
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public List<UploadFileDescription> getUploadedFiles() {
		return uploadedFiles;
	}
	private FileUtils() {
		// Util methods only
	}

	public static class UploadFileDescription {
		final String fileName;

		final byte[] fileData;

		UploadFileDescription(String fileName, byte[] fileData) {
			this.fileName = fileName;
			this.fileData = fileData;
		}

		public String getFileName() {
			return fileName;
		}

		public byte[] getFileData() {
			return fileData;
		}
	}

	public static FileUtils getRequestForm(HttpServletRequest httpServletRequest) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(
				new File(System.getProperty("java.io.tmpdir")));
		factory.setSizeThreshold(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
		factory.setFileCleaningTracker(null);

		ServletFileUpload upload = new ServletFileUpload(factory);
		FileUtils requestForm = new FileUtils();

		if(isMultipart) {
			try {
				List items = upload.parseRequest(httpServletRequest);
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					if (!item.isFormField()) {
						InputStream uploadedStream = item.getInputStream();
						requestForm.addUploadedFile(new UploadFileDescription(item.getName(), IOUtils.toByteArray(uploadedStream)));
//                                OutputStream out = new FileOutputStream("file.mov");) {
//
//                            IOUtils.copy(uploadedStream, out);
					} else  {
						requestForm.putParam(item.getFieldName(), item.getString());
					}
				}
			} catch (FileUploadException e) {


			} catch (IOException e) {

			}
		}
		return requestForm;
	}

}