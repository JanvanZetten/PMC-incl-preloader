/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmc.be;

import java.util.Comparator;

/**
 *
 * @author Asbamz
 */
public class StringWithIntegersComparator implements Comparator<String>
{
    @Override
    public int compare(String o1, String o2)
    {
        if (o1 == null && o2 == null)
        {
            return 0;
        }
        if (o1 == null)
        {
            return -1;
        }
        if (o2 == null)
        {
            return 1;
        }

        Integer i1 = null;
        try
        {
            i1 = Integer.valueOf(o1);
        }
        catch (NumberFormatException ignored)
        {
        }
        Integer i2 = null;
        try
        {
            i2 = Integer.valueOf(o2);
        }
        catch (NumberFormatException ignored)
        {
        }

        if (i1 == null && i2 == null)
        {
            return o1.compareTo(o2);
        }
        if (i1 == null)
        {
            return -1;
        }
        if (i2 == null)
        {
            return 1;
        }

        return i1 - i2;
    }
}
