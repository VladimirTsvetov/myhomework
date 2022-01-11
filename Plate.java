package lesson7;

public class Plate {

    private int food;

    public Plate(int food){

        this.food = food;
    }
    public int getFood() {
        return food;
    }

    public void eatDecreasing(int catAppetit) throws NoEatException{
        if(getFood() - catAppetit < 0){
            throw new NoEatException(getFood(),catAppetit,"ВНИМАНИЕ!  ");
        }
        food -= catAppetit;
    }

    public void loadFood(int food){
        this.food += food;
    }

    @Override
    public String toString(){
        return "на тарелке " + getFood() + " единиц еды для котов";
    }
}
