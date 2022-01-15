package xlinek.mendelu.xlinek.project_02.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
/*
    private String path;

    public NotFoundException(String path){
        this.path = path;
    }
 */
}
