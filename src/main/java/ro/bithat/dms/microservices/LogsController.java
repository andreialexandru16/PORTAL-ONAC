package ro.bithat.dms.microservices;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping()
public class LogsController {

	@GetMapping(value = "/logs", produces = {"application/json"})
	public String getLogs() {
		// Populates the array with names of files and directories
        String[] pathnames = new File("/logdata").list();

        return Stream.of(pathnames).collect( Collectors.joining( "\n" ) );
	}
	
//	@RequestMapping(value = "/logs/{fileName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@RequestMapping(value = "/logs/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> downloadStuff(@PathVariable String fileName) throws IOException {
	    String fullPath = "/logdata/" + fileName.replace("..", "").replace("/", "");
	    File file = new File(fullPath);
	    long fileLength = file.length(); // this is ok, but see note below

	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    respHeaders.setContentLength(fileLength);
	    respHeaders.setContentDispositionFormData("attachment", fileName);

	    return new ResponseEntity<FileSystemResource>(
	        new FileSystemResource(file), respHeaders, HttpStatus.OK
	    );
	}
}
