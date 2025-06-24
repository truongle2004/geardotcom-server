package web_ecommerce.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {
	
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	private String title = "Error in server";
	
    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(Class classResource, String message) {
        super(message);
        if(classResource != null)
        	title = String.format("Error while access %s", classResource.getSimpleName());
    }

	public HttpStatus getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
}
