package com.richlife.farma.RichLifeFarma;

import org.springframework.boot.SpringApplication;

public class TestRichLifeFarmaApplication {

	public static void main(String[] args) {
		SpringApplication.from(RichLifeFarmaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
