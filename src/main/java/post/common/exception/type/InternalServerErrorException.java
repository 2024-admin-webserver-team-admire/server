package post.common.exception.type;


import post.common.exception.ApplicationException;

public class InternalServerErrorException extends ApplicationException {

    public InternalServerErrorException(String message) {
        super(message, 500);
    }
}
