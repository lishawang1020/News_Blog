package NowNewsBlog.exceptions;

public class SubblogNotFoundException extends RuntimeException {
  public SubblogNotFoundException(String message) {
    super(message);
  }
}