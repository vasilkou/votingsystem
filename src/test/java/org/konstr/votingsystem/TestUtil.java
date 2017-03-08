package org.konstr.votingsystem;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Yury Vasilkou
 * Date: 08-Mar-17.
 */
public class TestUtil {
    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
