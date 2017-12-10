import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Arrays;
import java.util.Random;

public class TSP implements Constant{

    static int[][] dists = new int[numCities][numCities];

    public static void main(String[] args){
        MakeGraphDistances(randomWithRange(1, 9));
        result();
    }

    public static int result(){
        int[][] ants = InitAnts(numAnts, numCities);
        double[][] pheromones =  InitPheromones();

        int[] bestTrail = BestTrail(ants);
        int bestPathlength = PathLength(bestTrail);

        int time = 0;
        while(time < maxTime){
            // update all ants with new trails
            UpdateAnts(ants, pheromones);
            UpdatePheromones(pheromones, ants);

            int[] newbestTrail = BestTrail(ants);
            int newbestPathlength = PathLength(newbestTrail);

            if(newbestPathlength < bestPathlength){
                bestPathlength = newbestPathlength;
                bestTrail = newbestTrail;
            }
            time++;
        }

        return bestPathlength;
    }

    public static int randomWithRange(int min, int max){
        Random rg = new Random();
        return rg.nextInt(max-min) + min;
    }

    public static void MakeGraphDistances(int d) {
        for (int i = 0; i < numCities; i++){
            for (int j = 0; j < numCities; j++) {
                if(i == j){
                    dists[i][j]=0;
                } else{
                    dists[i][j] = d;
                    dists[j][i] = d;
                }
            }
        }
    }

    public static int Distance(int cityX, int cityY){
        return dists[cityX][cityY];
    }

    public static int[][] InitAnts(int numAnts, int numCities){
        int[][] ants = new int[numAnts][numCities];
        for (int k = 0; k < numAnts; k++) {
            int start = randomWithRange(0, numCities);
            ants[k] = RandomTrail(start, numCities);
        }
        return ants;
    }

    public static int[] RandomTrail(int start, int numCities){
        int[] trail = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            trail[i] = i;
        }

        for (int i = 0; i < numCities; i++) {
            int r = randomWithRange(i, numCities);
            int tmp = trail[r];
            trail[r] = trail[i];
            trail[i] = tmp;
        }

        int idx = IndexOfTarget(trail, start);
        int temp = trail[0];
        trail[0] = trail[idx];
        trail[idx] = temp;

        return trail;
    }

    public static int IndexOfTarget(int[] trail, int element){
        int elem =0;
        for(int i = 0; i < trail.length; i++){
            if(element == trail[i]){
                elem = i;
            }
        }
        return elem;
    }

    static double[][] InitPheromones(){
        double[][] pheromones = new double[numCities][numCities];

        for(int i = 0; i < numCities; i++){
            for(int j = 0; j < numCities; j++){
                pheromones[i][j] = 0.01;
            }
        }
        return pheromones;
    }

    static void UpdatePheromones(double[][] pheromones, int[][] ants){
        for(int i = 0; i < ants.length; i++){

            int antlength = ants[i].length;
            double length = Distance(ants[i][0], ants[i][antlength-1]);

            for(int j = 0; j < pheromones.length; j++){
                for(int k = 0; k < pheromones.length; k++){

                    double decrease = (1.0 - gamma)*pheromones[j][k];
                    double increase = 0.0;

                    if(EdgesInTrail(j, k, ants[i])){
                        increase = omega / length;
                    }
                }
            }
        }
    }

    public static boolean EdgesInTrail(int j, int k, int[] ant){
        boolean a = false;
        boolean b = false;

        for(int z = 0; z < ant.length; z++){
            if(j == ant[z]){
                a = true;
            }

            if(k == ant[z]){
                b = true;
            }
        }
        if(a && b){
            return true;
        }else{
            return false;
        }
    }

    static void UpdateAnts(int[][] ants, double[][] pheromones){

        for(int i = 0; i < ants.length; i++){
            int start = randomWithRange(0, numCities);

            //Build new trail for ant i
            int[] newTrail = BuildTrail(i, start, pheromones);
            ants[i] = newTrail;
        }
    }

    static int[] BuildTrail(int i, int start, double[][] pheromones){
        //Build new trail for ant i
        int[] trail = new int[numCities];
        boolean[] visited = new boolean[numCities];

        trail[0] = start;
        visited[start] = true;

        for (int k = 0; k < numCities - 1; k++){
            int cityX = trail[k];
            int next = NextCity(i, cityX, visited, pheromones, start);

            trail[k + 1] = next;
            visited[next] = true;
        }
        return trail;
    }

    static int NextCity(int k, int cityX, boolean[] visited, double[][] pheromones, int start){
        double[] probs = new double[numCities];
        double[] num = new double[numCities];
        double sum = 0.0;

        for(int i = 0; i < numCities; i++){
            if(i == cityX){
                num[i] =0.0;
            } else if(visited[i] == true){
                num[i] = 0.0;
            } else{
                num[i] = Math.pow(pheromones[cityX][i], alpha)*Math.pow((1.0/Distance(cityX, i)), beta);
            }

            if(num[i] < 0.0001){
                num[i] = 0.0001;
            }

            sum += num[i];
        }

        for(int i = 0; i < numCities; ++i){
            probs[i] = num[i] / sum;
        }

        double[] array = new double[numCities + 1];

        array[0] = 0;
        for(int i = 0; i < numCities; i++){
            array[i + 1] = array[i] + probs[i];
        }

        int nextCity = start;
        while(visited[nextCity] == true)
        {
            double p = Math.random();
            for(int i = 0; i < array.length - 1 ; ++i){
                if(p >= array[i] && p < array[i+1]){
                    nextCity = i;
                }
            }
        }
        return nextCity;
    }

    public static int[] BestTrail(int[][] ants){
        int[] pathlengths = new int[ants.length];

        for(int i = 0; i < ants.length; i++){
            pathlengths[i] = PathLength(ants[i]);
        }

        int minIndex = 0;
        for(int j = 0; j < pathlengths.length - 1; j++){

            if(pathlengths[j + 1] < pathlengths[minIndex]){
                minIndex = j + 1;
            }
        }
        return ants[minIndex];

    }

    public static int PathLength(int[] ant){
        int pathlength = 0;

        for(int i = 0; i < numCities - 1; i++){
            pathlength += Distance(ant[i], ant[i + 1]);
        }
        return pathlength;
    }
}