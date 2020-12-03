// Do not remove imports
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Scanner;

class TypeVariablesInspector {
    public void printTypeVariablesCount(TestClass obj, String methodName) throws Exception {
        // Add implementation here
        Method method = obj.getClass().getDeclaredMethod(methodName);
        TypeVariable[] typeVariables = method.getTypeParameters();
        System.out.println(typeVariables.length);
    }
}