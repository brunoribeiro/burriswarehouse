/*
 * Copyright (C) 2001-2005 Pleasant nightmare studio
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.jmex.bui.enumeratedConstants;

/**
 * @author deus
 * @version 1.0
 * @since Feb 18, 2009 12:38:10 PM
 */
public enum Orientation {
    HORIZONTAL(0),
    VERTICAL(1),
    OVERLAPPING(2); // Used only for Label instances, should we extract this to other, LabelOrientation enum?

    public int stylesheetId;

    Orientation(int stylesheetId) {
        this.stylesheetId = stylesheetId;
    }

    public static Orientation fromInt(int value) {
        for (Orientation orientation : values())
            if (orientation.stylesheetId == value)
                return orientation;
        throw new IllegalArgumentException("Orientation non-existent for value: " + value);
    }

}
