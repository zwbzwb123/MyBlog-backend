
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.UUID;


public class MailTest {

    @Test
    private void test() throws MessagingException {
        UUID.randomUUID().toString().substring(3);

    }
}
