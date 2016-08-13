package Genetic;

public abstract class ChromosomeDouble extends ChromosomeAbstract
{
    protected Double f;

    @Override
    public Double getEvaluation() {
        return this.f;
    }

    @Override
    public void setEvaluation(Object evaluation) {
        this.f = (Double) evaluation;
    }

    @Override
    public int compareTo(Object o) {
        ChromosomeDouble chromosomeDouble = (ChromosomeDouble) o;
        if (chromosomeDouble == null) {
            return 1;
        } else {
            return this.f.compareTo(chromosomeDouble.f);
        }
    }
}
