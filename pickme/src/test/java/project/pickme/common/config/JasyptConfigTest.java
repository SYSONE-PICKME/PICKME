package project.pickme.common.config;

import static org.junit.jupiter.api.Assertions.*;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptTest {
	@Autowired
	@Qualifier("jasyptStringEncryptor")
	private StringEncryptor stringEncryptor;

	@Test
	@DisplayName("평문을 암호화한 후 복호화한 결과는 평문과 일치한다.")
	public void testEncryptionDecryption() {
		// given
		String plainText = "jasypt";

		// when
		String encrypted = stringEncryptor.encrypt(plainText);
		String decrypted = stringEncryptor.decrypt(encrypted);

		// then
		assertEquals(plainText, decrypted);
	}
}
