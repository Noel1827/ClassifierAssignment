import java.awt.Color;

public class ImageClassifier {

    // Creates a feature vector (1D array) from the given picture.
    public static double[] extractFeatures(Picture picture) {
        int width = picture.width();
        int height = picture.height();
        int GRAY_VALUE;
        int n = width * height;
        double[] length = new double[n];
        int counter = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Color color = picture.get(y, x);
                GRAY_VALUE = color.getRed();
                length[counter] = GRAY_VALUE;
                counter++;
            }
        }
        return length;
    }

    // See below.
    public static void main(String[] args) {
        In training = new In(args[0]);
        int m = training.readInt();
        int w = training.readInt();
        int h = training.readInt();
        int n = w * h;
        MultiPerceptron classifier = new MultiPerceptron(m, n);
        while (!training.isEmpty()) {
            String files = training.readString();
            int label = training.readInt();
            Picture image = new Picture(files);
            classifier.trainMulti(extractFeatures(image), label);

        }

        In testing = new In(args[1]);
        m = testing.readInt();
        w = testing.readInt();
        h = testing.readInt();
        int incorrectc = 0;
        int totalc = 0;
        while (!testing.isEmpty()) {
            String files = testing.readString();
            int label = testing.readInt();
            Picture image = new Picture(files);
            int predict = classifier.predictMulti(extractFeatures(image));
            if (predict != label) {
                System.out.println(files);
                incorrectc++;
            }
            totalc++;
        }
        System.out.println("test error rate = " + ((double) incorrectc / totalc));
    }

}



