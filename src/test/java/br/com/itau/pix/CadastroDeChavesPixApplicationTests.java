package br.com.itau.pix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CadastroDeChavesPixApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		assertDoesNotThrow(() -> CadastroDeChavesPixApplication.main(new String[]{"a", "b"}));

	}

}
