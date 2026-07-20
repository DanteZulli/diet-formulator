package ar.dantezulli.diet_formulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DietFormulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietFormulatorApplication.class, args);
	}

}
