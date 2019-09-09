package in.indigenous.last.shelter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import in.indigenous.last.shelter.processors.skills.BulwarkProcessor;
import in.indigenous.last.shelter.processors.skills.DamageProcessor;
import in.indigenous.last.shelter.processors.skills.EnemyMightProcessor;
import in.indigenous.last.shelter.processors.skills.EnemyResistanceProcessor;
import in.indigenous.last.shelter.processors.skills.MarchingCapacityProcessor;
import in.indigenous.last.shelter.processors.skills.MightProcessor;
import in.indigenous.last.shelter.processors.skills.ResistanceProcessor;
import in.indigenous.last.shelter.processors.skills.SeigeDefenseMightProcessor;
import in.indigenous.last.shelter.processors.skills.SeigeMightProcessor;
import in.indigenous.last.shelter.processors.skills.SkillProcessor;

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
	
	@Bean
	public List<SkillProcessor> processors() {
		List<SkillProcessor> processors = new ArrayList<>();
		processors.add(new MarchingCapacityProcessor());
		processors.add(new DamageProcessor());
		processors.add(new BulwarkProcessor());
		processors.add(new MightProcessor());
		processors.add(new ResistanceProcessor());
		processors.add(new SeigeDefenseMightProcessor());
		processors.add(new SeigeMightProcessor());
		processors.add(new EnemyMightProcessor());
		processors.add(new EnemyResistanceProcessor());
		return processors;
	}

}
