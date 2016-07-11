package com.kirichko.jesus;

import android.content.Context;
import android.widget.Toast;

import com.kirichko.jesus.Utils.IndividualComparator;
import com.kirichko.jesus.model.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Created by MKirichko on 08.07.2016.
 */
public class World {

    //From 0 to VALUE_RANGE
    final int VALUE_RANGE = 5;
    final int ARRAY_LENGTH = 10;
    final int START_POPULATION_NUMBER = 3000;
    final int AVERAGE_ALIVE_POPULATION_NUMBER = 3000;
    final int TOP_CAP_SEASONS = 200;
    final float LUCKY_TOP_PERCENT = 0.3f;
    final int STOP_KILL = Math.round(AVERAGE_ALIVE_POPULATION_NUMBER /2.0f);
    final float PAIR_PROBABILITY = 0.0001f;
    final long MAX_MS = 100000;

    public void god()
    {
        //stupidRandom();
        startEndlessLifeCycle(generateStartPopulation(START_POPULATION_NUMBER, ARRAY_LENGTH, VALUE_RANGE), TOP_CAP_SEASONS);
    }

    private ArrayList<Individual> generateStartPopulation(int startPopulationNumber, int arrayLength, int valueRange)
    {
        ArrayList<Individual> population = new ArrayList<>(startPopulationNumber);

        for(int i = 0; i < startPopulationNumber; ++i)
        {
            Individual individual = new Individual(arrayLength, valueRange);
            population.add(individual);
        }

        return population;
    }

    private void startEndlessLifeCycle(ArrayList<Individual> startPopulation, int topCapSeasons)
    {
        ArrayList<Individual> currentPopulation = startPopulation;
        boolean jesusWasBorn = false;
        Individual jesus = new Individual(ARRAY_LENGTH, VALUE_RANGE);

        long startTime = System.currentTimeMillis();
        for(int i = 0; i<topCapSeasons && !jesusWasBorn; ++i)
        {
            currentPopulation = sexAndDeaths(currentPopulation, jesus);

            if(anyJesusHere(currentPopulation, jesus))
            {
                jesusWasBorn = true;
                long jesusTime = System.currentTimeMillis()- startTime;
                showLog("Jesus time: "+jesusTime + " ms");
                break;
            }
            if(System.currentTimeMillis()- startTime > MAX_MS)
            {
                showLog("Time_Abort");
                break;
            }
        }

        if(!jesusWasBorn)
        {
            showLog("Jesus reject you're foul world");
            showLog("Best was: "+currentPopulation.get(0).getUnlikeJesusPoints());
        }
    }

    private boolean anyJesusHere(ArrayList<Individual> population, Individual jesus)
    {
        boolean isJesusFind = false;

        for(Individual individual : population)
        {
            if(individual.isLike(jesus))
            {
                isJesusFind = true;
            }
        }

        return isJesusFind;
    }

    private ArrayList<Individual> sexAndDeaths(ArrayList<Individual> population, Individual jesus)
    {
        ArrayList<Individual> currentPopulation = population;
        compareAllWithJesus(currentPopulation,jesus);
        currentPopulation = killDweebs(currentPopulation, LUCKY_TOP_PERCENT);
        int bestOfGeneration = currentPopulation.get(0).getUnlikeJesusPoints();
        currentPopulation = sexAlpha(currentPopulation);
        return currentPopulation;
    }

    private void compareAllWithJesus(ArrayList<Individual> currentPopulation, Individual jesus)
    {
        for(Individual individual : currentPopulation)
        {
             individual.compareWithJesus(jesus);
        }
    }

    private ArrayList<Individual> killDweebs(ArrayList<Individual> currentPopulation, float luckyTopPercent)
    {
        Collections.sort(currentPopulation, new IndividualComparator());
        int alive = Math.round(currentPopulation.size()*luckyTopPercent);
        if(alive<STOP_KILL)
        {
            alive = STOP_KILL;
        }
        if(alive>currentPopulation.size()-1)
        {
            return currentPopulation;
        } else {
            return new ArrayList<>(currentPopulation.subList(0, alive));
        }
    }

    private ArrayList<Individual> sexAlpha(ArrayList<Individual> population)
    {
        ArrayList<Individual> newChildrens = new ArrayList<>();
        float childrenPerPairNeed = AVERAGE_ALIVE_POPULATION_NUMBER /
                ( (population.size() * population.size()) / 2.0f) / PAIR_PROBABILITY;


        Random r = new Random();
        for(int i = 0; i<population.size(); ++i)
        {
            for(int j = 0; j<population.size(); ++j)
            {

                r.setSeed(System.currentTimeMillis());
                if(r.nextInt(100) < PAIR_PROBABILITY*100)
                {
                    newChildrens.addAll(population.get(i).makeChilds(population.get(j), childrenPerPairNeed));
                }
            }
        }
        if(newChildrens.size()<1)
        {
            return population;
        } else {
            return newChildrens;
        }
    }

    private void stupidRandom()
    {
        boolean jesusFoundRandom = false;
        int lowest = 10000;
        Individual jesus = new Individual(ARRAY_LENGTH, VALUE_RANGE);
        for(int i = 0; i< 10000000; ++i)
        {
            Individual candidate = new Individual(ARRAY_LENGTH, VALUE_RANGE);
            candidate.compareWithJesus(jesus);
            if(lowest>candidate.getUnlikeJesusPoints())
            {
                lowest = candidate.getUnlikeJesusPoints();
            }
            if(candidate.getUnlikeJesusPoints()<4)
            {
                jesusFoundRandom = true;
                break;
            }
        }
        showLog(String.valueOf(lowest));
        int i = 4;
        int d = 2;
    }

    Context context;
    public void setContext(Context context)
    {
        this.context = context;
    }

    private void showLog(String s)
    {
        if(context!=null) Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

}