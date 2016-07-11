package com.kirichko.jesus.model;

/**
 * Created by Киричко on 08.07.2016.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by MKirichko on 08.07.2016.
 */
public class Individual
{
    private int[] traits;
    private static Random rnd;
    private int arrayLength;
    private int valueRange;

    private int unlikeJesusPoints = 50000;
    public int getUnlikeJesusPoints() {
        return unlikeJesusPoints;
    }

    public Individual(int arrayLength, int valueRange)
    {
        this.arrayLength = arrayLength;
        this.valueRange = valueRange;
        if(rnd == null) {
            rnd = new Random();
            rnd.setSeed(System.currentTimeMillis());
        }

        traits = new int[arrayLength];
        for(int i = 0; i < arrayLength; ++i)
        {
            traits[i] = rnd.nextInt(valueRange);
        }
    }

    public Individual(int arrayLength, int valueRange, int[] traits)
    {
        this.arrayLength = arrayLength;
        this.valueRange = valueRange;
        this.traits = traits;
    }

    public boolean isLike(Individual individual)
    {
        return Arrays.equals(traits, individual.traits);
    }

    public void compareWithJesus(Individual jesus)
    {
        unlikeJesusPoints = 0;
        for(int i = 0; i < traits.length; ++i)
        {
            unlikeJesusPoints+=Math.abs(traits[i] - jesus.traits[i]);
        }
    }

    public ArrayList<Individual> makeChilds(Individual partner, float childrenCount)
    {
        Random childRnd = new Random();
        childRnd.setSeed(System.currentTimeMillis());
        ArrayList<Individual> childs = new ArrayList<>();

        if(childrenCount<1)
        {
            Random r = new Random();
            r.setSeed(System.currentTimeMillis());
            if(r.nextInt(100) < childrenCount*100)
            {
                childrenCount = 1;
            } else
            {
                childrenCount = 0;
            }
        }
        for(int i = 0 ; i<childrenCount; ++i)
        {
            Individual child = new Individual(arrayLength,valueRange);
            int[] childrenTraits = new int[arrayLength];
            for(int j = 0; j<arrayLength; ++j)
            {
                if(traits[j]!=partner.traits[j]) {
                    if (traits[j] < partner.traits[j]) {
                        childrenTraits[j] = childRnd.nextInt(partner.traits[j] - traits[j]) + traits[j];
                    } else {
                        childrenTraits[j] = childRnd.nextInt(traits[j] - partner.traits[j]) + partner.traits[j];
                    }
                } else
                {
                    childrenTraits[j] = traits[j];
                }
            }
            child.traits = childrenTraits;
            childs.add(child);
        }

        return childs;
    }
}
