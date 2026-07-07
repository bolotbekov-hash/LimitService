package bakaibank.PracticeWork2.exception;

public class ResourceNotFoundException extends RuntimeException {
  public String getMessage() { return super.getMessage(); }
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
