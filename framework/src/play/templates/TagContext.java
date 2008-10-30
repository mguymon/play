package play.templates;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TagContext {
    
    static ThreadLocal<Stack<TagContext>> currentStack = new ThreadLocal();
    
    public String tagName;
    public Map<String,Object> data = new HashMap();
    
    public static void init() {
        currentStack.set(new Stack());
        enterTag("ROOT");
    }
    
    public static void enterTag(String name) {
        currentStack.get().add(new TagContext());
    }
    
    public static void exitTag() {
        currentStack.get().pop();
    }
    
    public static TagContext current() {
        return currentStack.get().peek();
    }
    
    public static TagContext parent() {
        if(currentStack.get().size() < 2) {
            return null;
        }
        return currentStack.get().get(currentStack.get().size()-2);
    }
    
    public static boolean hasParentTag(String name) {
        for(int i=currentStack.get().size()-1; i>=0; i--) {
            if(name.equals(currentStack.get().get(i).tagName)) {
                return true;
            }
        }
        return false;
    }
    
    public static TagContext parent(String name) {
        for(int i=currentStack.get().size()-1; i>=0; i--) {
            if(name.equals(currentStack.get().get(i).tagName)) {
                return currentStack.get().get(i);
            }
        }
        return null;
    }

}
