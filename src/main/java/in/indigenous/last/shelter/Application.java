package in.indigenous.last.shelter;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileLocator;
import org.apache.commons.configuration2.io.FileLocatorUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.common.util.io.excel.XLSXFileReader;

@EnableAutoConfiguration
@ComponentScan
@SpringBootConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	public PropertiesConfiguration configuration() {
		FileLocator.FileLocatorBuilder builder = FileLocatorUtils.fileLocator();
		FileLocator locator = builder.create();
		PropertiesConfiguration config = new PropertiesConfiguration();
		config.initFileLocator(locator);
		try {
			config.read(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("last-shelter.properties")));
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	@Bean
	public FileReader excelFileReader() {
		return new XLSXFileReader();
	}

}
