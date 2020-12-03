import java.lang.reflect.Method;

class MethodFinder {

    public static String findMethod(String methodName, String[] classNames) {
        for (String className : classNames) {
            try {
                Class clazz = Class.forName(className);
                for (Method method : clazz.getMethods()) {
                    if (methodName.equals(method.getName())) {
                        return clazz.getName();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}