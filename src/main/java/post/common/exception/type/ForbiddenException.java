package post.common.exception.type;

import post.common.exception.ApplicationException;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException(String message) {
        super(message, 403);
    }
}
