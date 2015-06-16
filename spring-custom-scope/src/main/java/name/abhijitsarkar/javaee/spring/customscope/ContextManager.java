package name.abhijitsarkar.javaee.spring.customscope;

public interface ContextManager {
    Object get(String name, String conversationId);
    Object remove(String name, String conversationId);
}
