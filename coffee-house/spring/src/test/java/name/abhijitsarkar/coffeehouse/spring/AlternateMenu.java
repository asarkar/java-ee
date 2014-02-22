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

package name.abhijitsarkar.coffeehouse.spring;

import name.abhijitsarkar.coffeehouse.Coffee;
import name.abhijitsarkar.coffeehouse.Menu;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */

@Named
@Primary
public class AlternateMenu extends Menu {
    private List<Coffee.Blend> blends;
    private List<Coffee.Flavor> flavors;

    @PostConstruct
    public void postConstruct() {
        /* Hate to create a List from a List but asList() returns a barebone implementation that doesn't support
         * the remove() operation.
         */
        final List<Coffee.Blend> blendsOnTheMenu = new ArrayList<>(Arrays.asList(Coffee.Blend.values()));
        blendsOnTheMenu.remove(Coffee.Blend.DECAF);

        final List<Coffee.Flavor> flavorsOnTheMenu = new ArrayList<>(Arrays.asList(Coffee.Flavor.values()));
        flavorsOnTheMenu.remove(Coffee.Flavor.MOCHA);

        this.setBlends(blendsOnTheMenu);
        this.setFlavors(flavorsOnTheMenu);
    }
}
