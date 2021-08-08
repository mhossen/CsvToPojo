package Interface;

public interface ICsvType {
    IDelimiter WithDelimiter(char delimiter);
    IDelimiter WithDelimiter(String delimiter);
}
