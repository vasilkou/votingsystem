package org.konstr.votingsystem;

import org.junit.Assert;
import org.konstr.votingsystem.util.ValidationUtil;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class TestUtil {
    public static <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(ValidationUtil.getRootCause(e), instanceOf(exceptionClass));
        }
    }
}
