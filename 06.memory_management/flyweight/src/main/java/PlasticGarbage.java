public class PlasticGarbage implements Garbage {

    private String shape;
    private String garbage1;
    private String garbage2;
    private String garbage3;
    private String garbage4;
    private String garbage5;
    private String garbage6;

    public PlasticGarbage(String shape) {
        this.shape = shape;
        this.garbage1 = shape + 1;
        this.garbage2 = shape + 2;
        this.garbage3 = shape + 3;
        this.garbage4 = shape + 4;
        this.garbage5 = shape + 5;
        this.garbage6 = shape + 6;
    }

    @Override
    public void generate() {
        System.out.println("PlasticGarbage: generate() - " + shape);
    }

}
