package post.common.exception.type;

import post.common.exception.ApplicationException;

public class ConflictException extends ApplicationException {

    public ConflictException(String message) {
        super(message, 409);
    }
}
