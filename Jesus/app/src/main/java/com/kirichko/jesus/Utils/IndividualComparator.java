package com.kirichko.jesus.Utils;

import com.kirichko.jesus.model.Individual;

import java.util.Comparator;

/**
 * Created by Киричко on 08.07.2016.
 */
public class IndividualComparator  implements Comparator<Individual> {
        @Override
        public int compare(Individual i1, Individual i2) {
            if(i1.getUnlikeJesusPoints() == i2.getUnlikeJesusPoints()) return 0;
            return (i1.getUnlikeJesusPoints() > i2.getUnlikeJesusPoints()) ? 1 : -1;
        }
}
