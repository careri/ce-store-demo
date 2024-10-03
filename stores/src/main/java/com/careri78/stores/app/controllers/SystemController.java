package com.careri78.stores.app.controllers;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {

	private final Environment env;

	public SystemController(final Environment env) {
		this.env = env;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(path = "")
	public ResponseEntity<Properties> get() {
		// Copied from https://stackoverflow.com/questions/23506471/access-all-environment-properties-as-a-map-or-properties-object
		Properties props = new Properties();
		MutablePropertySources propSrcs = ((AbstractEnvironment) env).getPropertySources();
		StreamSupport.stream(propSrcs.spliterator(), false)
				.filter(ps -> ps instanceof EnumerablePropertySource)
				.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
				.flatMap(Arrays::<String>stream)
				.forEach(propName -> props.setProperty(propName, env.getProperty(propName)));
		return ResponseEntity.ok(props);
	}
}