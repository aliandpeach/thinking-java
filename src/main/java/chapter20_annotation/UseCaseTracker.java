package chapter20_annotation;//: origin.annotations/UseCaseTracker.java

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 简单的注解处理器
 */
public class UseCaseTracker {
    /**
     * 注解处理
     */
    public static void processAnnotation() {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 47, 48, 49, 50);
        trackUseCases(useCases, PasswordUtils.class);
    }

    /**
     *
     * @param useCases
     * @param cl
     */
    private static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case:" + uc.id() + " "
                        + uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int i : useCases) {
            System.out.println("Warning: Missing use case-" + i);
        }
    }
} /*
 * Output: Found Use Case:47 Passwords must contain at least one numeric Found
 * Use Case:48 no description Found Use Case:49 New passwords can't equal
 * previously used ones Warning: Missing use case-50
 */// :~
