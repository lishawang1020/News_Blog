package NowNewsBlog.exceptions;

public class NowNewsBlogException extends RuntimeException {
  public NowNewsBlogException(String exceptionMessage) {
    super(exceptionMessage);
  }
  public NowNewsBlogException(String exceptionMessage, Exception exception) {
    super(exceptionMessage, exception);
  }
}
