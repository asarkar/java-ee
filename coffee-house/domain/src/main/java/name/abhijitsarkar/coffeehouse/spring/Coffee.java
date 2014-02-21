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

/**
 * @author Abhijit Sarkar
 */
public class Coffee {
    private final Blend blend;
    private final Flavor flavor;
    private int sugar;
    private int cream;

    public Coffee(Blend blend) {
        this(blend, Flavor.NONE);
    }

    public Coffee(Blend blend, Flavor flavor) {
        this.blend = blend;
        this.flavor = flavor;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public void setCream(int cream) {
        this.cream = cream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coffee coffee = (Coffee) o;

        if (cream != coffee.cream) return false;
        if (sugar != coffee.sugar) return false;
        if (blend != coffee.blend) return false;
        if (flavor != coffee.flavor) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = blend.hashCode();
        result = 31 * result + (flavor != null ? flavor.hashCode() : 0);
        result = 31 * result + sugar;
        result = 31 * result + cream;

        return result;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "blend=" + blend +
                ", flavor=" + flavor +
                ", sugar=" + sugar +
                ", cream=" + cream +
                '}';
    }

    public enum Blend {
        DARK, MEDIUM, DECAF, NONE;
    }

    public enum Flavor {
        VANILLA, CARAMEL, MOCHA, NONE;
    }
}

