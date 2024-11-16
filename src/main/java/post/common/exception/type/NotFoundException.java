package post.common.exception.type;

import post.common.exception.ApplicationException;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String message) {
        super(message, 404);
    }
}
