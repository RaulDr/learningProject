package dirtyBits.learningProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dirtyBits.learningProject.config.Config;

@Configuration
@SpringBootApplication(scanBasePackages = "justToStopAutoscan")
@Import({Config.class})
public class LeaningProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaningProjectApplication.class, args);
	}
}
