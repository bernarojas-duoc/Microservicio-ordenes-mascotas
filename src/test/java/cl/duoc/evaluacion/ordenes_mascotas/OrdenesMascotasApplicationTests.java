package cl.duoc.evaluacion.ordenes_mascotas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdenesMascotasApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	void testBeanOrdenCompraControllerExiste() {
		assertTrue(applicationContext.containsBean("ordenCompraController"));
	}

	@Test
	void testBeanOrdenCompraServiceExiste() {
		assertTrue(applicationContext.containsBean("ordenCompraService"));
	}

	@Test
	void testMain() {
		assertDoesNotThrow(() ->
				OrdenesMascotasApplication.main(new String[]{"--server.port=0"}));
	}
}
