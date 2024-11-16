package post.common.exception.type;

import post.common.exception.ApplicationException;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(String message) {
        super(message, 401);
    }
}
