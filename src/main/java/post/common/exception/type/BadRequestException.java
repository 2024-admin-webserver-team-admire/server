package post.common.exception.type;

import post.common.exception.ApplicationException;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message, 400);
    }
}
