import java.util.*;
public class Genetic {
  double[][] population;
  int[] fitness;
  final int size = 7; //---population size
  int limit; //---number of generations
  static double[] bestRace; //----the best chromosome resulted from genetic algorithm


    public Genetic(int limit) {
        this.population = new double[size][4];
        this.fitness = new int[size]; //fitness number for chromosome in the population
        this.limit = limit;
    }
    public void populate(){
        //creates the population and populates each chromosome of it with random genes
        Random rand = new Random();
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < 4 ; j++) {
                this.population[i][j] = rand.nextInt(80);
            }

        }
    }

    public void resetFitness(){
        Arrays.fill(fitness, 0);
    }

    //------plays every twp player and fills the corresponding fitness function based on the number of wins
    public void playOff() throws Exception {
        resetFitness();
        AI agent1 = new AI('A');
        AI agent2 = new AI('B');
        Quoridor game = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i != j){
                    agent1.setChromosome(population[i]);
                    agent2.setChromosome(population[j]);
                    game = new Quoridor(agent1 , agent2);
                    game.inTraining = true;
                    agent1.setGame(game);
                    agent2.setGame(game);
                    game.setMoveCap(120);
                    game.simulating = true;
                    game.play();

                    if(!game.tieGame) {
                        AI winner = (AI) game.winner;
                        if (Arrays.equals(winner.chromosome, population[i]))
                            fitness[i]++;
                        else if (Arrays.equals(winner.chromosome, population[j]))
                            fitness[j]++;
                    }
                }
            }
        }
    }
    

    //---creates a new generation consisting of bests from the previous generation and replaces worsts with the superior genes
    //from the crossover operation
    //---num determines the number of best parents to select from the population

    public void evolution(int num){

        //num * (num - 1) / 2 : the number of worst chromosomes in a generation which are replaced with new values
        var superior = selection(num);
        Stack<double[]> newGen = new Stack<>();
        for (int i = 0; i < superior.size(); i++) {
            for (int j = 0; j < superior.size(); j++) {
                if(i != j)
                {
                   newGen.push( crossover(superior.get(i) , superior.get(j)) );
                }
            }
        }
        int len = (num * (num-1)) / 2;

        for (int i = 0; i < len ; i++) {
            int temp = smallestIndex(fitness);
            population[temp] = newGen.pop();
        }

        
    }

    public ArrayList<double[]> selection(int number){

        //---selects "number" best members of the population
        int[] fitnessCopy = new int[size];
        System.arraycopy(fitness, 0, fitnessCopy, 0, size);
        ArrayList<double[]> superior = new ArrayList<>();
        
        for (int i = 0 ; i < number ; i++){
            int temp = largestIndex(fitnessCopy);
            superior.add(population[temp]);
            fitnessCopy[temp] = 0;
        }
        
        return superior;

    }

    public double[] crossover(double[] father, double[] mother){

        Random random = new Random();
        double[] offspring = new double[4];
        int point = random.nextInt(4); //0 - 1 - 2 - 3
        if (point >= 0) System.arraycopy(father, 0, offspring, 0, point);
        if (4 - point >= 0) System.arraycopy(mother, point, offspring, point, 4 - point);

        var chance = Math.random(); //--determines chance of mutation
        if(chance > 0.6)
            mutation(offspring); //randomly chooses a gene and mutates it
        
        return offspring;

    }

    private void mutation(double[] offspring) {
        Random random = new Random();
        int index = random.nextInt(4);
        int value = random.nextInt(80);
        offspring[index] = value;

    }

    public double[] findBestChromosome(int evolutionNumber) throws Exception {
        int c = 0;
        for (int i = 0; i < limit; i++) {
            c++;
            System.err.println("generation"+(i+1));
            playOff();
            evolution(evolutionNumber); //----->( num * (num - 1) ) / 2  must be smaller than the population size
        }
        playOff();
        int temp = largestIndex(fitness);
        System.out.println(c+" generations were simulated, superior chromosome:");
        return population[temp];


    }

    static int largestIndex(int[] arr)
    {
        int i;
        int max = arr[0];
        int index = 0;
        for (i = 1; i < arr.length; i++) {
            if (arr[i] > max)
            {
                max = arr[i];
                index = i;

            }
        }
        return index;
    }

    static int smallestIndex(int[] arr)
    {
        int i;
        int min = arr[0];
        int index = 0;
        for (i = 1; i < arr.length; i++) {
            if (arr[i] < min)
            {
                min = arr[i];
                index = i;

            }
        }
        return index;
    }



}


class training{
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        //--------------------------------------------
        System.out.println("how many generations?");
        int generationLimit = input.nextInt();
        Genetic genetic = new Genetic(generationLimit);
        genetic.populate();


        System.out.println("enter the evolution number \n" +
                "(the number of new generation members which replaces the previous generation = \n" +
                "(evolution number) * (evolution number - 1) / 2)");

        int evolutionNumber = input.nextInt();
        Genetic.bestRace = genetic.findBestChromosome(evolutionNumber);
        //----copy these numbers in AI chromosome
        for (var value : Genetic.bestRace) {
            System.out.print(value+", ");
        }


    }
}