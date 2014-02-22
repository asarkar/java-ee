/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

package name.abhijitsarkar.coffeehouse.spring.support;

import name.abhijitsarkar.coffeehouse.Coffee;
import name.abhijitsarkar.coffeehouse.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */

@Configuration
@ComponentScan(basePackages = "name.abhijitsarkar.coffeehouse.spring")
@EnableAspectJAutoProxy
public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private Menu menu;

    @Bean
    Menu menu() {
        if (menu != null) {
            LOGGER.debug("Found menu type: {}.", menu.getClass().getSimpleName());

            return menu;
        }

        LOGGER.debug("Creating new menu.");

        menu = new Menu();

        final List<Coffee.Blend> blendsOnTheMenu = Arrays.asList(Coffee.Blend.values());
        final List<Coffee.Flavor> flavorsOnTheMenu = Arrays.asList(Coffee.Flavor.values());

        menu.setBlends(blendsOnTheMenu);
        menu.setFlavors(flavorsOnTheMenu);

        return menu;
    }
}
