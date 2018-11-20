package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.WelcomeController1;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeControllerTest {

    @Test
    public void itSaysHello() throws Exception {
        WelcomeController1 controller = new WelcomeController1("A welcome message");

        assertThat(controller.sayHello()).isEqualTo("A welcome message");
    }
}
